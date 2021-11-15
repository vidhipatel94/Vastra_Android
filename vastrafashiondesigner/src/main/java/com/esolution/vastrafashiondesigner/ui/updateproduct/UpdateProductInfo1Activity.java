package com.esolution.vastrafashiondesigner.ui.updateproduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductType;
import com.esolution.vastrafashiondesigner.ui.newproduct.AddProductInfo1Activity;
import com.esolution.vastrafashiondesigner.ui.newproduct.AddProductInfo2Activity;

public class UpdateProductInfo1Activity extends AddProductInfo1Activity {

    private static final String EXTRA_PRODUCT = "extra_product";

    public static Intent createIntent(Context context, Catalogue catalogue, Product product) {
        Intent intent = new Intent(context, UpdateProductInfo1Activity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fillData();
    }

    @Override
    protected boolean getIntentData() {
        if (getIntent() != null) {
            product = (Product) getIntent().getSerializableExtra(EXTRA_PRODUCT);
        }
        return super.getIntentData() && product != null;
    }

    private void fillData() {
        ProductType productType = product.getProductType();
        if (productType == null) return;

        switch (productType.getGender()) {
            case ProductType.GENDER_MALE:
                binding.rbMale.setChecked(true);
                break;
            case ProductType.GENDER_FEMALE:
                binding.rbFemale.setChecked(true);
                break;
            case ProductType.GENDER_BOTH:
                binding.rbBoth.setChecked(true);
                break;
        }

        switch (productType.getAgeGroup()) {
            case ProductType.AGE_GROUP_KIDS:
                binding.rbKids.setChecked(true);
                break;
            case ProductType.AGE_GROUP_BABY:
                binding.rbBaby.setChecked(true);
                break;
            case ProductType.AGE_GROUP_ADULTS:
                binding.rbAdult.setChecked(true);
                break;
        }

        selectedProductTypeId = productType.getId();
        binding.inputProductType.setText(productType.getName());
    }

    @Override
    protected void openNextScreen() {
        startActivity(UpdateProductInfo2Activity.createIntent(this, catalogue, product));
    }
}
