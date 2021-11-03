package com.esolution.vastrafashiondesigner.ui.newproduct.addsize;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.BasicProduct;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductSize;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.databinding.ActivityProductSizesBinding;
import com.esolution.vastrafashiondesigner.databinding.ListSizeTitleBinding;
import com.esolution.vastrafashiondesigner.ui.newproduct.inventory.AddProductInventoryActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductSizesActivity extends BaseActivity {

    private static final String EXTRA_CATALOGUE = "extra_catalogue";
    private static final String EXTRA_PRODUCT = "extra_product";

    public static Intent createIntent(Context context, Catalogue catalogue, Product product) {
        Intent intent = new Intent(context, ProductSizesActivity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    private ActivityProductSizesBinding binding;
    private ProgressDialogHandler progressDialogHandler;

    private Catalogue catalogue;
    private Product product;

    private ArrayList<ProductSize> productSizes = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityProductSizesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogHandler = new ProgressDialogHandler(this);

        initView();
    }

    @Override
    protected View getRootView() {
        return binding.getRoot();
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            catalogue = (Catalogue) getIntent().getSerializableExtra(EXTRA_CATALOGUE);
            product = (Product) getIntent().getSerializableExtra(EXTRA_PRODUCT);
            return catalogue != null && product != null;
        }
        return false;
    }

    private void initView() {
        setToolbarLayout();

        getSimilarProducts();

        setSizesTitleLayout();
        setGridLayout();

        binding.btnUpdateSizes.setOnClickListener((v) -> openAddSizesDialog());

        binding.btnNext.setOnClickListener((v) -> {
            if (isFormValidated()) {
                saveProduct();
            }
        });
    }

    private void getSimilarProducts() {
        progressDialogHandler.setProgress(true);

        DesignerLoginPreferences preferences = DesignerLoginPreferences.createInstance(this);
        int designerId = preferences.getDesignerId();
        String token = preferences.getSessionToken();

        subscriptions.add(RestUtils.getAPIs()
                .getDesignerProductsByTypes(token, designerId, product.getTypeId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        List<BasicProduct> similarProducts = response.getData();
                        if (similarProducts != null && !similarProducts.isEmpty()) {
                            openSelectPrevAddedSizesDialog(similarProducts);
                            return;
                        }
                    }
                    openAddSizesDialog();
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialogHandler.setProgress(false);
                    openAddSizesDialog();
                }));
    }

    private void setToolbarLayout() {
        binding.toolbarLayout.iconBack.setOnClickListener((view) -> onBackPressed());
        binding.toolbarLayout.title.setText(R.string.title_product_sizes);
    }

    private void openSelectPrevAddedSizesDialog(List<BasicProduct> similarProducts) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SelectPrevAddedSizesDialog dialog = new SelectPrevAddedSizesDialog(similarProducts,
                new SelectPrevAddedSizesDialog.PrevAddedSizesListener() {
                    @Override
                    public void onClickAddCustomSizes() {
                        openAddSizesDialog();
                    }
                });
        dialog.show(fragmentManager, SelectPrevAddedSizesDialog.class.getName());
    }

    private void openAddSizesDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddSizesDialog dialog = new AddSizesDialog(product.getId(), new AddSizesDialog.Listener() {
            @Override
            public void onSizeIsSelected(ArrayList<ProductSize> productSizes) {
                ProductSizesActivity.this.productSizes = productSizes;
                binding.sizesLayout.setVisibility(productSizes.isEmpty() ? View.INVISIBLE : View.VISIBLE);
                setSizesTitleLayout();
                setGridLayout();
            }
        });
        dialog.show(fragmentManager, AddSizesDialog.class.getName());
    }

    private void setSizesTitleLayout() {
        binding.sizesTitleLayout.removeAllViews();

        ListSizeTitleBinding emptyBinding = ListSizeTitleBinding.inflate(LayoutInflater.from(this),
                binding.sizesTitleLayout, false);
        binding.sizesTitleLayout.addView(emptyBinding.getRoot());

        for (int i = 0; i < productSizes.size(); i++) {
            ListSizeTitleBinding sizeBinding = ListSizeTitleBinding.inflate(LayoutInflater.from(this),
                    binding.sizesTitleLayout, false);
            sizeBinding.textView.setText(productSizes.get(i).getSizeText(this));
            binding.sizesTitleLayout.addView(sizeBinding.getRoot());
        }
    }

    private SizeAdapter adapter;

    private void setGridLayout() {
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add(getString(R.string.label_US_size));
        titleList.add(getString(R.string.label_front_length));
        titleList.add(getString(R.string.label_back_length));
        titleList.add(getString(R.string.label_width));
        titleList.add(getString(R.string.label_head_circumference));
        titleList.add(getString(R.string.label_neck));
        titleList.add(getString(R.string.label_bust));
        titleList.add(getString(R.string.label_waist));
        titleList.add(getString(R.string.label_hip));
        titleList.add(getString(R.string.label_inseam_length));
        titleList.add(getString(R.string.label_outseam_length));
        titleList.add(getString(R.string.label_sleeve_length));
        titleList.add(getString(R.string.label_wrist));
        titleList.add(getString(R.string.label_foot_length));

        int totalParameters = titleList.size();

        ArrayList<String> measurementList = new ArrayList<>();
        for (int i = 0; i < totalParameters * productSizes.size(); i++) {
            measurementList.add(null);
        }

        binding.gridView.setNumColumns(totalParameters);
        adapter = new SizeAdapter(titleList, measurementList);
        binding.gridView.setAdapter(adapter);

        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < totalParameters) {
                    return;
                }

                int column = position % totalParameters;
                String parameter = titleList.get(column);

                int row = (int) Math.floor(position / (float) totalParameters) - 1;
                ProductSize productSize = productSizes.get(row);

                int measurementIndex = position - totalParameters;

                FragmentManager fragmentManager = getSupportFragmentManager();
                SizeMeasurementDialog dialog = SizeMeasurementDialog.newInstance(parameter, productSize,
                        productSize1 -> {
                            if (updateProductSize(row, productSize1)) {
                                measurementList.set(measurementIndex, getMeasurementText(productSize1, parameter));
                                adapter.notifyDataSetChanged();
                            }
                        });
                dialog.show(fragmentManager, SizeMeasurementDialog.class.getName());
            }
        });
    }

    private String getMeasurementText(ProductSize productSize, String parameter) {
        if (productSize == null || TextUtils.isEmpty(parameter)) return null;

        String text = null;
        if (parameter.equals(getString(R.string.label_US_size))) {
            text = productSize.getUSSize();
        } else if (parameter.equals(getString(R.string.label_front_length))) {
            text = String.valueOf(productSize.getFrontLength());
        } else if (parameter.equals(getString(R.string.label_back_length))) {
            text = String.valueOf(productSize.getBackLength());
        } else if (parameter.equals(getString(R.string.label_width))) {
            text = String.valueOf(productSize.getWidth());
        } else if (parameter.equals(getString(R.string.label_head_circumference))) {
            if (productSize.getHeadCircumferenceMin() == productSize.getHeadCircumferenceMax()) {
                text = String.valueOf(productSize.getHeadCircumferenceMin());
            } else {
                text = productSize.getHeadCircumferenceMin() + " - " + productSize.getHeadCircumferenceMax();
            }
        } else if (parameter.equals(getString(R.string.label_neck))) {
            if (productSize.getNeckMin() == productSize.getNeckMax()) {
                text = String.valueOf(productSize.getNeckMin());
            } else {
                text = productSize.getNeckMin() + " - " + productSize.getNeckMax();
            }
        } else if (parameter.equals(getString(R.string.label_bust))) {
            if (productSize.getBustMin() == productSize.getBustMax()) {
                text = String.valueOf(productSize.getBustMin());
            } else {
                text = productSize.getBustMin() + " - " + productSize.getBustMax();
            }
        } else if (parameter.equals(getString(R.string.label_waist))) {
            if (productSize.getWaistMin() == productSize.getWaistMax()) {
                text = String.valueOf(productSize.getWaistMin());
            } else {
                text = productSize.getWaistMin() + " - " + productSize.getWaistMax();
            }
        } else if (parameter.equals(getString(R.string.label_hip))) {
            if (productSize.getHipMin() == productSize.getHipMax()) {
                text = String.valueOf(productSize.getHipMin());
            } else {
                text = productSize.getHipMin() + " - " + productSize.getHipMax();
            }
        } else if (parameter.equals(getString(R.string.label_inseam_length))) {
            text = String.valueOf(productSize.getInseamLength());
        } else if (parameter.equals(getString(R.string.label_outseam_length))) {
            text = String.valueOf(productSize.getOutseamLength());
        } else if (parameter.equals(getString(R.string.label_sleeve_length))) {
            text = String.valueOf(productSize.getSleeveLength());
        } else if (parameter.equals(getString(R.string.label_wrist))) {
            if (productSize.getWristMin() == productSize.getWristMax()) {
                text = String.valueOf(productSize.getWristMin());
            } else {
                text = productSize.getWristMin() + " - " + productSize.getWristMax();
            }
        } else if (parameter.equals(getString(R.string.label_foot_length))) {
            text = String.valueOf(productSize.getFootLength());
        }
        if (text != null && text.equals("0.0")) {
            text = null;
        }

        return text;
    }

    private boolean updateProductSize(int index, ProductSize productSize) {
        if (productSize == null || index < 0 || index >= productSizes.size()) {
            return false;
        }
        productSizes.set(index, productSize);
        return true;
    }

    private boolean isFormValidated() {
        if (productSizes.isEmpty()) {
            showMessage(getRootView(), getString(R.string.error_empty_size));
            return false;
        }
        product.setSizes(productSizes);
        return true;
    }

    private void saveProduct() {
        progressDialogHandler.setProgress(true);
        DesignerLoginPreferences preferences = DesignerLoginPreferences.createInstance(this);
        subscriptions.add(RestUtils.getAPIs().createProduct(preferences.getSessionToken(), product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        Product product = response.getData();
                        if (product == null || product.getColors() == null || product.getColors().isEmpty() ||
                                product.getSizes() == null || product.getSizes().isEmpty()) {
                            showMessage(binding.getRoot(), getString(R.string.server_error));
                        } else {
                            DesignerLoginPreferences.createInstance(this).setAnyCatalogueAdded(true);
                            openNextScreen(response.getData());
                        }
                    } else {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(this, throwable);
                    showMessage(binding.getRoot(), message);
                }));
    }

    private void openNextScreen(Product product) {
        Intent intent = AddProductInventoryActivity.createIntent(this, product);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
