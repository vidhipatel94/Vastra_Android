package com.esolution.vastrafashiondesigner.ui.newproduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.Material;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductMaterial;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductInfo2Binding;
import com.esolution.vastrafashiondesigner.databinding.RowAddProductMaterialBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddProductInfo2Activity extends BaseActivity {

    private static final String EXTRA_CATALOGUE = "extra_catalogue";
    private static final String EXTRA_PRODUCT = "extra_product";

    public static Intent createIntent(Context context, Catalogue catalogue, Product product) {
        Intent intent = new Intent(context, AddProductInfo2Activity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    private ActivityAddProductInfo2Binding binding;
    private ProgressDialogHandler progressDialogHandler;

    private Catalogue catalogue;
    private Product product;

    private List<Material> materials = new ArrayList<>();
    private ArrayAdapter<String> materialAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityAddProductInfo2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogHandler = new ProgressDialogHandler(this);

        initView();

        getMaterials();
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

    private final List<RowAddProductMaterialBinding> materialBindingList = new ArrayList<>();

    private void initView() {
        binding.toolbarLayout.title.setText(catalogue.getName());
        binding.toolbarLayout.iconBack.setOnClickListener(v -> onBackPressed());

        binding.parentLinearLayout.setOnClickListener(v -> closeKeyboard());

        binding.materialLayout1.iconDelete.setVisibility(View.GONE);

        binding.linkAddMore.setOnClickListener(v -> {
            RowAddProductMaterialBinding materialBinding = RowAddProductMaterialBinding.inflate(getLayoutInflater());
            binding.addMoreMaterialLayout.addView(materialBinding.getRoot());
            materialBindingList.add(materialBinding);
            if (materialAdapter != null) {
                materialBinding.autoCompleteProductMaterial.setAdapter(materialAdapter);
            }

            materialBinding.iconDelete.setOnClickListener((v1) -> {
                binding.addMoreMaterialLayout.removeView(materialBinding.getRoot());
                materialBindingList.remove(materialBinding);
            });
        });

        binding.btnNext.setOnClickListener(v -> {
            closeKeyboard();
            if (isFormValidated()) {
                openNextScreen();
            }
        });
    }

    private void getMaterials() {
        progressDialogHandler.setProgress(true);
        subscriptions.add(RestUtils.getAPIs().getMaterials()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        if (response.getData() != null) {
                            materials = response.getData();
                            Collections.sort(materials);
                            updateMaterialAdapter();
                        } else {
                            showMessage(binding.getRoot(), getString(R.string.server_error));
                        }
                    } else {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(this, throwable);
                    showMessage(binding.getRoot(), message);
                }));
    }

    private void updateMaterialAdapter() {
        String[] materialsArr = new String[materials.size()];
        for (int i = 0; i < materials.size(); i++) {
            materialsArr[i] = materials.get(i).getMaterial();
        }

        materialAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, materialsArr);
        binding.materialLayout1.autoCompleteProductMaterial.setAdapter(materialAdapter);
        for (RowAddProductMaterialBinding materialBinding : materialBindingList) {
            materialBinding.autoCompleteProductMaterial.setAdapter(materialAdapter);
        }
    }

    private boolean isFormValidated() {
        String title = binding.inputProductTitle.getText().toString().trim();
        if (title.isEmpty()) {
            binding.inputLayoutProductTitle.setErrorEnabled(true);
            binding.inputLayoutProductTitle.setError(getString(R.string.error_empty_title));
            return false;
        } else {
            binding.inputLayoutProductTitle.setErrorEnabled(false);
        }

        String priceStr = binding.inputProductPrice.getText().toString().trim();
        float price = 0f;
        if (priceStr.isEmpty()) {
            binding.inputLayoutProductPrice.setErrorEnabled(true);
            binding.inputLayoutProductPrice.setError(getString(R.string.error_empty_product_price));
            return false;
        } else {
            try {
                price = Float.parseFloat(priceStr);
            } catch (NumberFormatException e) {
                binding.inputLayoutProductPrice.setErrorEnabled(true);
                binding.inputLayoutProductPrice.setError(getString(R.string.error_invalid_product_price));
                return false;
            }
        }
        if (price == 0) {
            binding.inputLayoutProductPrice.setErrorEnabled(true);
            binding.inputLayoutProductPrice.setError(getString(R.string.error_zero_product_price));
            return false;
        }
        binding.inputLayoutProductPrice.setErrorEnabled(false);

        String multipackSetStr = binding.inputMultipackSet.getText().toString().trim();
        int multipackSet = 0;
        if (!TextUtils.isEmpty(multipackSetStr)) {
            try {
                multipackSet = Integer.parseInt(multipackSetStr);
            } catch (NumberFormatException ignored) {
            }
        }

        String weightStr = binding.inputProductWeight.getText().toString().trim();
        float weight = 0f;
        if (weightStr.isEmpty()) {
            binding.inputLayoutProductWeight.setErrorEnabled(true);
            binding.inputLayoutProductWeight.setError(getString(R.string.error_empty_product_weight));
            return false;
        } else {
            try {
                weight = Float.parseFloat(weightStr);
            } catch (NumberFormatException e) {
                binding.inputLayoutProductWeight.setErrorEnabled(true);
                binding.inputLayoutProductWeight.setError(getString(R.string.error_invalid_product_weight));
                return false;
            }
        }
        if (weight == 0) {
            binding.inputLayoutProductWeight.setErrorEnabled(true);
            binding.inputLayoutProductWeight.setError(getString(R.string.error_zero_product_weight));
            return false;
        }
        binding.inputLayoutProductWeight.setErrorEnabled(false);

        List<ProductMaterial> addedMaterials = new ArrayList<>();

        ProductMaterial material1 = getMaterialValidated(binding.materialLayout1);
        if (material1 == null) {
            return false;
        }
        addedMaterials.add(material1);

        int totalMaterialPercentage = material1.getPercentage();
        for (RowAddProductMaterialBinding materialBinding : materialBindingList) {
            ProductMaterial material = getMaterialValidated(materialBinding);
            if (material == null) {
                return false;
            }
            if (!isMaterialExists(addedMaterials, material)) {
                addedMaterials.add(material);
            } else {
                materialBinding.inputLayoutProductMaterial.setErrorEnabled(true);
                materialBinding.inputLayoutProductMaterial.setError(getString(R.string.error_duplicate_material));
                return false;
            }
            totalMaterialPercentage += material.getPercentage();

            if (totalMaterialPercentage > 100) {
                showMessage(binding.getRoot(), getString(R.string.error_invalid_total_material_percentage));
                return false;
            }
        }
        if (totalMaterialPercentage != 100) {
            showMessage(binding.getRoot(), getString(R.string.error_invalid_total_material_percentage));
            return false;
        }

        product.setTitle(title);
        product.setPrice(price);
        product.setMultipackSet(multipackSet);
        product.setWeight(weight);
        product.setMaterials(addedMaterials);

        return true;
    }

    private ProductMaterial getMaterialValidated(RowAddProductMaterialBinding materialBinding) {
        String materialName = materialBinding.autoCompleteProductMaterial.getText().toString().trim();
        if (materialName.isEmpty()) {
            materialBinding.inputLayoutProductMaterial.setErrorEnabled(true);
            materialBinding.inputLayoutProductMaterial.setError(getString(R.string.error_empty_material));
            return null;
        } else {
            materialBinding.inputLayoutProductMaterial.setErrorEnabled(false);
        }

        String materialPercentageStr = materialBinding.inputProductPercentage.getText().toString().trim();
        int materialPercentage = 0;
        if (materialPercentageStr.isEmpty()) {
            materialBinding.inputLayoutProductPercentage.setErrorEnabled(true);
            showMessage(binding.getRoot(), getString(R.string.error_empty_material_percentage));
            return null;
        } else {
            try {
                materialPercentage = Integer.parseInt(materialPercentageStr);
            } catch (NumberFormatException e) {
                materialBinding.inputLayoutProductPercentage.setErrorEnabled(true);
                showMessage(binding.getRoot(), getString(R.string.error_invalid_material_percentage));
                return null;
            }
        }
        if (materialPercentage == 0 || materialPercentage > 100) {
            materialBinding.inputLayoutProductPercentage.setErrorEnabled(true);
            showMessage(binding.getRoot(), getString(R.string.error_zero_material_percentage));
            return null;
        }
        materialBinding.inputLayoutProductPercentage.setErrorEnabled(false);

        ProductMaterial productMaterial = new ProductMaterial();
        productMaterial.setMaterialName(materialName);
        productMaterial.setPercentage(materialPercentage);

        for (Material material : materials) {
            if (material.getMaterial().equalsIgnoreCase(materialName)) {
                productMaterial.setMaterialId(material.getId());
                break;
            }
        }

        return productMaterial;
    }

    private boolean isMaterialExists(List<ProductMaterial> addedMaterials, ProductMaterial material) {
        for (ProductMaterial existMaterial : addedMaterials) {
            if (existMaterial.getMaterialName().equalsIgnoreCase(material.getMaterialName())) {
                return true;
            }
        }
        return false;
    }

    private void openNextScreen() {
        startActivity(AddProductInfo3Activity.createIntent(this, catalogue, product));
    }
}
