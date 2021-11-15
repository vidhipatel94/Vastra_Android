package com.esolution.vastrafashiondesigner.ui.updateproduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductOccasion;
import com.esolution.vastrabasic.models.product.ProductSeason;
import com.esolution.vastrabasic.models.product.Season;
import com.esolution.vastrafashiondesigner.ui.newproduct.AddProductImagesActivity;
import com.esolution.vastrafashiondesigner.ui.newproduct.AddProductInfo3Activity;

public class UpdateProductInfo3Activity extends AddProductInfo3Activity {

    public static Intent createIntent(Context context, Catalogue catalogue, Product product) {
        Intent intent = new Intent(context, UpdateProductInfo3Activity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fillData();
    }

    private void fillData() {
        if (selectedOccasions == null) {
            selectedOccasions = new boolean[OCCASIONS.length];
        }
        if (product.getOccasions() != null && !product.getOccasions().isEmpty()) {
            for (ProductOccasion occasion : product.getOccasions()) {
                if (occasion.getOccasion() > 0 && occasion.getOccasion() <= selectedOccasions.length) {
                    selectedOccasions[occasion.getOccasion() - 1] = true;
                }
            }
            onUpdateOccasions();
        }

        if (product.getSeasons() != null) {
            for (ProductSeason season : product.getSeasons()) {
                switch (season.getSeason()) {
                    case Season.WINTER:
                        binding.chkWinter.setChecked(true);
                        break;
                    case Season.SPRING:
                        binding.chkSpring.setChecked(true);
                        break;
                    case Season.SUMMER:
                        binding.chkSummer.setChecked(true);
                        break;
                    case Season.FALL:
                        binding.chkFall.setChecked(true);
                        break;
                }
            }
        }

        selectedPattern = product.getPattern();
        onUpdatePattern();
        switch (product.getKnitOrWoven()) {
            case Product.KNIT:
                binding.rbKnit.setChecked(true);
                break;
            case Product.WOVEN:
                binding.rbWoven.setChecked(true);
                break;
            case Product.NONE:
                binding.rbNone.setChecked(true);
                break;
        }

        selectedWashcare = product.getWashCare();
        onUpdateWashCare();

        binding.inputTrend.setText(product.getTrend());
    }

    @Override
    protected void openNextScreen() {
        startActivity(UpdateProductImagesActivity.createIntent(this, catalogue, product));
    }
}
