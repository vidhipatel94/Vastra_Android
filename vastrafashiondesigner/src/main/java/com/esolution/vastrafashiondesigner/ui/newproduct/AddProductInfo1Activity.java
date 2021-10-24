package com.esolution.vastrafashiondesigner.ui.newproduct;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductType;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductInfo1Binding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddProductInfo1Activity extends BaseActivity {

    private static final String EXTRA_CATALOGUE = "extra_catalogue";

    public static Intent createIntent(Context context, Catalogue catalogue) {
        Intent intent = new Intent(context, AddProductInfo1Activity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        return intent;
    }

    private ActivityAddProductInfo1Binding binding;
    private ProgressDialogHandler progressDialogHandler;

    private Catalogue catalogue;

    private List<ProductType> productTypes = new ArrayList<>();
    private int selectedProductTypeId = -1;

    private Product product;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityAddProductInfo1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarLayout.title.setText(catalogue.getName());
        binding.toolbarLayout.iconBack.setOnClickListener(v -> onBackPressed());

        progressDialogHandler = new ProgressDialogHandler(this);

        getProductTypes();

        initView();
    }

    @Override
    protected View getRootView() {
        return binding.getRoot();
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            catalogue = (Catalogue) getIntent().getSerializableExtra(EXTRA_CATALOGUE);
            return catalogue != null;
        }
        return false;
    }

    private void getProductTypes() {
//        productTypes.add(new ProductType(1, "Shirt", 0, 0));
//        productTypes.add(new ProductType(2, "TShirt", 1, 0));
//        productTypes.add(new ProductType(3, "Skirt", 2, 1));
//        productTypes.add(new ProductType(4, "Short", 1, 1));
//        productTypes.add(new ProductType(5, "Pant", 0, 0));
//        productTypes.add(new ProductType(6, "Jacket", 1, 0));
//        productTypes.add(new ProductType(7, "Shrug", 2, 0));
//        productTypes.add(new ProductType(8, "Belt", 0, 1));
//        productTypes.add(new ProductType(9, "Cap", 2, 0));
//        productTypes.add(new ProductType(10, "Watch", 0, 0));


        progressDialogHandler.setProgress(true);
        subscriptions.add(RestUtils.getAPIs().getProductTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        if (response.getData() != null) {
                            productTypes = response.getData();
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

    private void initView() {
        binding.parentLinearLayout.setOnClickListener(v -> closeKeyboard());

        binding.rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                binding.inputProductType.setText(null);
                filteredProductTypesMap.clear();
                selectedProductTypeId = -1;
                selectedProductTypePos = -1;
            }
        });

        binding.rgAgeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                binding.layoutAge.setVisibility(binding.rbKids.isChecked() ? View.VISIBLE : View.GONE);
                binding.inputProductType.setText(null);
                filteredProductTypesMap.clear();
                selectedProductTypeId = -1;
                selectedProductTypePos = -1;
            }
        });

        binding.inputProductType.setOnClickListener(v -> openProductTypesDialog());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValidated()) {
                    openNextScreen();
                }
            }
        });
    }

    private Map<String, Integer> filteredProductTypesMap = new HashMap<>();
    private int selectedProductTypePos = -1;

    private void openProductTypesDialog() {
        if (filteredProductTypesMap.isEmpty()) {
            int gender = ProductType.GENDER_BOTH;
            if (binding.rbMale.isChecked()) {
                gender = ProductType.GENDER_MALE;
            } else if (binding.rbFemale.isChecked()) {
                gender = ProductType.GENDER_FEMALE;
            }

            int ageGroup = ProductType.AGE_GROUP_ADULTS;
            if (binding.rbKids.isChecked()) {
                ageGroup = ProductType.AGE_GROUP_KIDS;
            }

            for (ProductType productType : productTypes) {
                if (productType.getGender() == gender && productType.getAgeGroup() == ageGroup) {
                    filteredProductTypesMap.put(productType.getName(), productType.getId());
                }
            }
        }
        String[] entries = filteredProductTypesMap.keySet().toArray(new String[filteredProductTypesMap.size()]);
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(entries, Math.max(0, selectedProductTypePos), null)
                .setTitle(R.string.label_product_type)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        selectedProductTypePos = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        String type = entries[selectedProductTypePos];
                        binding.inputProductType.setText(type);
                        try {
                            selectedProductTypeId = filteredProductTypesMap.get(type);
                        } catch (NullPointerException ignored) {
                        }
                    }
                }).show();
    }

    private boolean isFormValidated() {
        if (selectedProductTypeId == -1) {
            binding.inputLayoutProductType.setErrorEnabled(true);
            binding.inputLayoutProductType.setError(getString(R.string.error_empty_product_type));
            return false;
        }
        binding.inputLayoutProductType.setErrorEnabled(false);

        product = new Product(catalogue.getId(), selectedProductTypeId, null);

        if (binding.rbKids.isChecked()) {
            String minAgeStr = binding.inputMinAge.getText().toString().trim();
            if (minAgeStr.isEmpty()) {
                binding.inputLayoutMinAge.setErrorEnabled(true);
                binding.inputLayoutMinAge.setError(getString(R.string.error_empty_min_age));
                return false;
            }
            int minAge;
            try {
                minAge = Integer.parseInt(minAgeStr);
            } catch (NumberFormatException e) {
                binding.inputLayoutMinAge.setErrorEnabled(true);
                binding.inputLayoutMinAge.setError(getString(R.string.error_invalid_min_age));
                return false;
            }
            binding.inputLayoutMinAge.setErrorEnabled(false);

            String maxAgeStr = binding.inputMaxAge.getText().toString().trim();
            if (maxAgeStr.isEmpty()) {
                binding.inputLayoutMaxAge.setErrorEnabled(true);
                binding.inputLayoutMaxAge.setError(getString(R.string.error_empty_max_age));
                return false;
            }
            int maxAge;
            try {
                maxAge = Integer.parseInt(maxAgeStr);
            } catch (NumberFormatException e) {
                binding.inputLayoutMinAge.setErrorEnabled(true);
                binding.inputLayoutMinAge.setError(getString(R.string.error_invalid_max_age));
                return false;
            }
            binding.inputLayoutMaxAge.setErrorEnabled(false);

            if (minAge > maxAge) {
                showMessage(binding.getRoot(), getString(R.string.error_invalid_age_range));
                return false;
            }

            product.setMinAge(minAge);
            product.setMaxAge(maxAge);
        } else {
            product.setMinAge(0);
            product.setMaxAge(0);
        }

        return true;
    }

    private void openNextScreen() {
        startActivity(AddProductInfo2Activity.createIntent(this, catalogue, product));
    }
}
