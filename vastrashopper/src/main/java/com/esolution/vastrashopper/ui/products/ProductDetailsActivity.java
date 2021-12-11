package com.esolution.vastrashopper.ui.products;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductColor;
import com.esolution.vastrabasic.models.product.ProductMaterial;
import com.esolution.vastrabasic.models.product.ProductSize;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrabasic.utils.JsonUtils;
import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.data.ShopperLoginPreferences;
import com.esolution.vastrashopper.databinding.ActivityProductDetailsBinding;
import com.esolution.vastrashopper.databinding.RowColorProductDetailBinding;
import com.esolution.vastrashopper.databinding.RowSizeBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductDetailsActivity extends BaseActivity {

    public static final String EXTRA_IS_DESIGNER = "is_designer";

    protected CompositeDisposable subscriptions = new CompositeDisposable();
    private ActivityProductDetailsBinding binding;
    private Product product;
    private int productId = 0;
    private boolean isDesigner = false;
    private ProgressDialogHandler progressDialogHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());

        progressDialogHandler = new ProgressDialogHandler(this);
        setContentView(binding.getRoot());

        if (getIntentData()) {
            getProductInfo();
        } else {
            finish();
        }

        if (isDesigner) {
            binding.addToCartLayout.setVisibility(View.GONE);
        }
    }

    private boolean getIntentData() {
        Intent intent = getIntent();
        isDesigner = intent.getBooleanExtra(EXTRA_IS_DESIGNER, false);
        if (intent.hasExtra("ProductId")) {
            productId = (int) intent.getSerializableExtra("ProductId");
            return true;
        }
        return false;
    }

    private void getProductInfo() {
        progressDialogHandler.setProgress(true);
        ShopperLoginPreferences preferences = ShopperLoginPreferences.createInstance(this);
        subscriptions.add(RestUtils.getAPIs().getProductInfo(preferences.getSessionToken(), productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        if (response.getData() != null) {
                            product = response.getData();
                            setProductData(product);
                        }
                    } else {
                        //Log.i("------", "Failure: " + response.getMessage());
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(this, throwable);
                    //Log.i("-----", "throwable: " + throwable);
                    //Log.i("-----", "throwable: " + message);
                    showMessage(binding.getRoot(), message);
                }));
    }

    private void setProductData(Product product) {
        if (product == null) {
            return;
        }

        Log.d("-------", "setProductData: " + JsonUtils.toJson(product.getImages()));
        if (product.getImages() != null) {
            binding.viewPager.setAdapter(new ViewPagerAdapter(this, product.getImages()));
        }

        if (product.getOverallRating() != 0) {
            binding.rating.setText(String.valueOf(product.getOverallRating()));
        } else {
            binding.rating.setVisibility(View.INVISIBLE);
        }

        binding.brandName.setText(product.getBrandName());
        binding.title.setText(product.getTitle());
        binding.designerName.setText("By " + product.getDesignerName());
        binding.totalLikes.setText(String.valueOf(product.getTotalLikes()) + " Likes");
        binding.price.setText("$" + String.valueOf(product.getPrice()));

        binding.productDescription.setText(product.getDescription());

        setProductMaterialsCare();

        setProductSizesView();

        setProductColorsView();

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getInventories().size() == 0) {
                    openDialog(R.string.product_not_available_message);
                    return;
                }

                if (selectedSize == null) {
                    openDialog(R.string.select_size_message);
                    return;
                }

                if (selectedColor == null) {
                    openDialog(R.string.select_color_message);
                    return;
                }

                boolean isProductFound = false;
                for (int i = 0; i < product.getInventories().size(); i++) {
                    if (product.getInventories().get(i).getProductSizeId() == selectedSize.getId() &&
                            product.getInventories().get(i).getProductColorId() == selectedColor.getId() &&
                            product.getInventories().get(i).getQuantityAvailable() != 0) {
                        isProductFound = true;
                        break;
                    }
                }

                if (isProductFound) {
                    openNextScreen();
                } else {
                    openDialog(R.string.product_not_available_message);
                }
            }
        });
    }

    private void setProductMaterialsCare() {
        if (product.getMaterials().size() == 0) {
            return;
        }

        StringBuilder material = new StringBuilder();
        for (int i = 0; i < product.getMaterials().size(); i++) {
            ProductMaterial productMaterial = product.getMaterials().get(i);
            if (i == 0) {
                material.append(productMaterial.getMaterialName());
                material.append(" ");
                material.append(productMaterial.getPercentage());
                material.append("%");
            } else {
                material.append(", ");
                material.append(productMaterial.getMaterialName());
                material.append(" ");
                material.append(productMaterial.getPercentage());
                material.append("%");
            }
        }

        material.append("\n");
        if (product.getWashCare() == 0) {
            material.append("Machine-Wash");
        } else if (product.getWashCare() == 1) {
            material.append("Hand-Wash");
        } else {
            material.append("Dry Clean");
        }

        binding.materialCare.setText(String.valueOf(material));
    }

    private void openNextScreen() {
        Intent intent = new Intent(this, ProductCartActivity.class);
        startActivity(intent);
    }

    private void setProductSizesView() {
        // Set data for productSizes
        binding.sizeFlowLayout.removeAllViews();

        if (product.getSizes().size() == 0) {
            binding.emptySizesText.setVisibility(View.VISIBLE);
            return;
        }
        binding.emptySizesText.setVisibility(View.GONE);

        selectedSizeBinding = null;
        selectedSize = null;

        for (ProductSize productSize : product.getSizes()) {
            RowSizeBinding sizeBinding = RowSizeBinding.inflate(getLayoutInflater());
            binding.sizeFlowLayout.addView(sizeBinding.getRoot());
            sizeBinding.sizeTextView.setText(productSize.getSizeText(this));
            sizeBinding.sizeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSizeSelected(sizeBinding, productSize);
                }
            });
        }
    }

    private RowSizeBinding selectedSizeBinding;
    private ProductSize selectedSize = null;

    private void onSizeSelected(RowSizeBinding sizeBinding, ProductSize productSize) {
        if (selectedSizeBinding == sizeBinding) {
            return;
        }

        // another size is previously selected
        if (selectedSizeBinding != null) {
            selectedSizeBinding.sizeTextView.setBackground(ContextCompat.
                    getDrawable(this, R.drawable.bg_size_circle_view));
            selectedSizeBinding.sizeTextView.setTextColor(getResources().getColor(R.color.primary_dark));
        }

        sizeBinding.sizeTextView.setBackground(ContextCompat.
                getDrawable(this, R.drawable.bg_size_selected_circle_view));
        sizeBinding.sizeTextView.setTextColor(getResources().getColor(R.color.white));

        selectedSizeBinding = sizeBinding;
        selectedSize = productSize;
    }

    private void setProductColorsView() {
        // Set data for productColors
        binding.colorFlowLayout.removeAllViews();

        if (product.getColors().size() == 0) {
            binding.emptyColorsText.setVisibility(View.VISIBLE);
            return;
        }
        binding.emptyColorsText.setVisibility(View.GONE);

        selectedSizeBinding = null;
        selectedSize = null;

        for (ProductColor productColor : product.getColors()) {
            RowColorProductDetailBinding colorBinding = RowColorProductDetailBinding.inflate(getLayoutInflater());
            binding.colorFlowLayout.addView(colorBinding.getRoot());
            colorBinding.circleColorView.setColor(productColor.getProminentColorHexCode());
            colorBinding.circleColorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onColorSelected(colorBinding, productColor);
                }
            });
        }
    }

    private RowColorProductDetailBinding selectedColorBinding;
    private ProductColor selectedColor = null;

    private void onColorSelected(RowColorProductDetailBinding colorBinding, ProductColor productColor) {
        if (selectedColorBinding == colorBinding) {
            return;
        }

        // another color is previously selected
        if (selectedColorBinding != null) {
            selectedColorBinding.checkImage.setVisibility(View.INVISIBLE);
        }

        colorBinding.checkImage.setVisibility(View.VISIBLE);

        selectedColorBinding = colorBinding;
        selectedColor = productColor;
    }

    private void openDialog(int message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
