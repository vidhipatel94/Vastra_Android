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
import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductType;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductInfo1Binding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddProductInfo1Activity extends BaseActivity {

    protected static final String EXTRA_CATALOGUE = "extra_catalogue";

    public static Intent createIntent(Context context, Catalogue catalogue) {
        Intent intent = new Intent(context, AddProductInfo1Activity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        return intent;
    }

    protected ActivityAddProductInfo1Binding binding;
    private ProgressDialogHandler progressDialogHandler;

    protected Catalogue catalogue;

    private List<ProductType> productTypes = new ArrayList<>();
    protected int selectedProductTypeId = -1;

    protected Product product;

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

    protected boolean getIntentData() {
        if (getIntent() != null) {
            catalogue = (Catalogue) getIntent().getSerializableExtra(EXTRA_CATALOGUE);
            return catalogue != null;
        }
        return false;
    }

    private void getProductTypes() {
        progressDialogHandler.setProgress(true);
        subscriptions.add(RestUtils.getAPIs().getProductTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        if (response.getData() != null) {
                            productTypes = response.getData();
                            Collections.sort(productTypes);
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

        binding.rgGender.setOnCheckedChangeListener(onCheckedChangeListener);
        binding.rgAgeGroup.setOnCheckedChangeListener(onCheckedChangeListener);

        binding.inputProductType.setOnClickListener(v -> openProductTypesDialog());

        binding.btnNext.setOnClickListener(v -> {
            if (isFormValidated()) {
                openNextScreen();
            }
        });
    }

    private final RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            binding.inputProductType.setText(null);
            filteredProductTypesMap.clear();
            selectedProductTypeId = -1;
            selectedProductTypePos = -1;
        }
    };

    private final Map<String, Integer> filteredProductTypesMap = new LinkedHashMap<>();
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
            } else if (binding.rbBaby.isChecked()) {
                ageGroup = ProductType.AGE_GROUP_BABY;
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
                        if (selectedProductTypePos >= 0 && entries.length > 0) {
                            String type = entries[selectedProductTypePos];
                            binding.inputProductType.setText(type);
                            try {
                                selectedProductTypeId = filteredProductTypesMap.get(type);
                            } catch (NullPointerException ignored) {
                            }
                        } else {
                            binding.inputProductType.setText(null);
                            selectedProductTypeId = -1;
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

        Designer designer = DesignerLoginPreferences.createInstance(this).getDesigner();

        if (product == null) {
            product = new Product(designer.getId(), catalogue.getId(), selectedProductTypeId, null);
        } else {
            product.setTypeId(selectedProductTypeId);
        }
        return true;
    }

    protected void openNextScreen() {
        startActivity(AddProductInfo2Activity.createIntent(this, catalogue, product));
    }
}
