package com.esolution.vastrashopper.ui.products;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductSize;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrabasic.utils.JsonUtils;
import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.data.ShopperLoginPreferences;
import com.esolution.vastrashopper.databinding.ActivityProductDetailsBinding;
import com.esolution.vastrashopper.databinding.RowColorProductDetailBinding;
import com.esolution.vastrashopper.databinding.RowSizeBinding;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductDetailsActivity extends BaseActivity {

    protected CompositeDisposable subscriptions = new CompositeDisposable();
    private ActivityProductDetailsBinding binding;
    private ArrayList<RowSizeBinding> rowSizeBindingArrayList = new ArrayList<>();
    private ArrayList<RowColorProductDetailBinding> rowColorProductDetailBinding = new ArrayList<>();
    private Product product;
    private int productId = 0;
    private ProgressDialogHandler progressDialogHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());

        progressDialogHandler = new ProgressDialogHandler(this);
        setContentView(binding.getRoot());

        if (getIntentData()) {
            getProductInfo();
        }
    }

    private boolean getIntentData() {
        Intent intent = getIntent();

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

        binding.designerName.setText("By " + product.getDesignerName());
        binding.title.setText(product.getTitle());

        if(product.getOverallRating() != 0) {
            binding.rating.setText(String.valueOf(product.getOverallRating()));
        } else {
            binding.rating.setVisibility(View.INVISIBLE);
        }

        // Materials TODO

        // Product Details Occasion TODO

        binding.price.setText("$" + String.valueOf(product.getPrice()));
        binding.totalLikes.setText(String.valueOf(product.getTotalLikes()) + " Likes");

        if (product.getImages() != null) {
            binding.viewPager.setAdapter(new ViewPagerAdapter(this, product.getImages()));
        }

        // Set data for productSizes
        binding.sizeFlowLayout.removeAllViews();
        //Log.i("-----", "sizes: " + JsonUtils.toJson(product.getSizes()));
        for (int i = 0; i < product.getSizes().size(); i++) {
            rowSizeBindingArrayList.add(RowSizeBinding.inflate(getLayoutInflater()));
            binding.sizeFlowLayout.addView(rowSizeBindingArrayList.get(i).getRoot());
            //Log.i("-----", "sizes: " + product.getSizes().get(i));
            if(product.getSizes().get(i).getBrandSize() != null) {
                rowSizeBindingArrayList.get(i).sizeTextView.setText(product.getSizes().get(i).getBrandSize());
            } else if (product.getSizes().get(i).getUSSize() != null) {
                rowSizeBindingArrayList.get(i).sizeTextView.setText(product.getSizes().get(i).getUSSize());
            } else if (product.getSizes().get(i).getCustomSize() != null) {
                rowSizeBindingArrayList.get(i).sizeTextView.setText(product.getSizes().get(i).getCustomSize());
            } else {
                rowSizeBindingArrayList.get(i).sizeTextView.setText(product.getSizes().get(i).getSizeText(this));
            }
        }

        if (product.getSizes().size() == 0) {
            rowSizeBindingArrayList.add(RowSizeBinding.inflate(getLayoutInflater()));
            binding.sizeFlowLayout.addView(rowSizeBindingArrayList.get(0).getRoot());
            rowSizeBindingArrayList.get(0).sizeTextView.setVisibility(View.INVISIBLE);
            rowSizeBindingArrayList.get(0).msgNoSizeTextView.setVisibility(View.VISIBLE);
            rowSizeBindingArrayList.get(0).msgNoSizeTextView.setText(R.string.noSizeFound);
        }

        final boolean[] isSizeSelected = {false};
        final int[] selectedSize = {0};
        for (int i = 0; i < product.getSizes().size(); i++) {
            int finalI = i;
            rowSizeBindingArrayList.get(i).sizeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSizeSelected[0]) {
                        isSizeSelected[0] = false;
                        rowSizeBindingArrayList.get(finalI).sizeTextView.setBackground(ContextCompat.
                                getDrawable(getApplicationContext(), R.drawable.bg_size_circle_view));
                        rowSizeBindingArrayList.get(finalI).sizeTextView.setTextColor(getResources().getColor(R.color.primary_dark));
                    } else {
                        isSizeSelected[0] = true;
                        //Log.i("------", "onClick: ");
                        selectedSize[0] = product.getSizes().get(finalI).getId();
                        rowSizeBindingArrayList.get(finalI).sizeTextView.setBackground(ContextCompat.
                                getDrawable(getApplicationContext(), R.drawable.bg_size_selected_circle_view));
                        rowSizeBindingArrayList.get(finalI).sizeTextView.setTextColor(getResources().getColor(R.color.white));
                    }
                }
            });
        }

        // Set data for productColors
        binding.colorFlowLayout.removeAllViews();
        for (int i = 0; i < product.getColors().size(); i++) {
            rowColorProductDetailBinding.add(RowColorProductDetailBinding.inflate(getLayoutInflater()));
            binding.colorFlowLayout.addView(rowColorProductDetailBinding.get(i).getRoot());
            if(product.getColors().get(i).getProminentColorName() != null) {
                //Log.i("----", "prominentColor: " + product.getColors().get(i).getProminentColorName());
                rowColorProductDetailBinding.get(i).circleColorView.setColor(product.getColors().get(i).getProminentColorHexCode());
            }
            if(product.getColors().get(i).getSecondaryColorName() != null) {
                //Log.i("----", "secondaryColor: " + product.getColors().get(i).getSecondaryColorName());
                rowColorProductDetailBinding.get(i).circleColorView.setColor(product.getColors().get(i).getSecondaryColorHexCode());
            }
            if(product.getColors().get(i).getThirdColorName() != null) {
                //Log.i("----", "thirdColor: " + product.getColors().get(i).getThirdColorName());
                rowColorProductDetailBinding.get(i).circleColorView.setColor(product.getColors().get(i).getThirdColorHexCode());
            }
        }

        if (product.getColors().size() == 0) {
            rowColorProductDetailBinding.add(RowColorProductDetailBinding.inflate(getLayoutInflater()));
            binding.colorFlowLayout.addView(rowColorProductDetailBinding.get(0).getRoot());
            rowColorProductDetailBinding.get(0).circleColorView.setVisibility(View.INVISIBLE);
            rowColorProductDetailBinding.get(0).msgNoColorTextView.setVisibility(View.VISIBLE);
            rowColorProductDetailBinding.get(0).msgNoColorTextView.setText(R.string.noColorFound);
        }

        final boolean[] isColorSelected = {false};
        final int[] selectedColor = {0};
        for (int i = 0; i < product.getColors().size(); i++) {
            int finalI = i;
            rowColorProductDetailBinding.get(i).circleColorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isColorSelected[0]) {
                        isColorSelected[0] = false;
                        rowColorProductDetailBinding.get(finalI).checkImage.setVisibility(View.INVISIBLE);
                    } else {
                        isColorSelected[0] = true;
                        //Log.i("------", "onClick: ");
                        selectedColor[0] = product.getColors().get(finalI).getId();
                        rowColorProductDetailBinding.get(finalI).checkImage.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getInventories().size() == 0) {
                    openDialog(R.string.product_not_available_message);
                }

                for (int i = 0; i < product.getInventories().size(); i++) {
                    if (product.getInventories().get(i).getProductSizeId() == selectedSize[0] &&
                            product.getInventories().get(i).getProductColorId() == selectedColor[0]) {
                        if (product.getInventories().get(i).getQuantityAvailable() != 0) {
                            openDialog(R.string.product_available_message);
                        } else {
                            openDialog(R.string.product_not_available_message);
                        }
                    } else {
                        openDialog(R.string.select_product_message);
                    }
                }
            }
        });
    }

    private void openDialog(int message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.add_to_cart)
                .setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
