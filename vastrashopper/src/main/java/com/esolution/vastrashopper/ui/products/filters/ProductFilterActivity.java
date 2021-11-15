package com.esolution.vastrashopper.ui.products.filters;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastrabasic.models.ProductFilter;
import com.esolution.vastrabasic.utils.JsonUtils;
import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.databinding.ActivityProductFiltersBinding;

import java.util.ArrayList;

public class ProductFilterActivity extends AppCompatActivity {

    private static final int TYPE = 1;
    private static final int PRICE = 2;
    private static final int PATTERN = 3;
    private static final int KNIT_WOVEN = 4;
    private static final int WASH_CARE = 5;
    private static final int COLOR = 6;
    private static final int MATERIAL = 7;
    private static final int OCCASION = 8;
    private static final int SEASON = 9;
    private static final int SIZE = 10;
    private static final int DESIGNER = 11;

    private ActivityProductFiltersBinding binding;
    private ProductFilter productFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductFiltersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productFilter = new ProductFilter();

        loadFragment(new TypeFragment(productFilter.getProductTypes(),
                productFilter.getAgeGroup(), productFilter.getGender()), TYPE);
        changeFilterTypeView(TYPE);

        initFragment();

        binding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Log.i("------", "MinPrice: " + productFilter.getMinPrice());
                Log.i("------", "MaxPrice: " + productFilter.getMaxPrice());
                Log.i("------", "Type: " + productFilter.getProductTypes());
                Log.i("------", "Pattern: " + productFilter.getProductPatterns());
                Log.i("------", "Knit/Woven: " + productFilter.getProductKnitWovens());
                Log.i("------", "WashCare: " + productFilter.getProductWashCares());
                Log.i("------", "Color: " + productFilter.getProductColors());
                Log.i("------", "Material: " + productFilter.getProductMaterials());
                Log.i("------", "Occasion: " + productFilter.getProductOccasions());
                Log.i("------", "Season: " + productFilter.getProductSeasons());
                Log.i("------", "BrandSize: " + productFilter.getProductBrandSizes());
                Log.i("------", "CustomSize: " + productFilter.getProductCustomSizes());
                Log.i("------", "Designer: " + productFilter.getProductDesigners());*/
                savePrevLoadedFilterData();

                ProductFilter productFilterObj = new ProductFilter(productFilter.getProductTypes(),
                        productFilter.getMinPrice(),
                        productFilter.getMaxPrice(),
                        productFilter.getProductPatterns(),
                        productFilter.getProductKnitWovens(), productFilter.getProductWashCares(),
                        productFilter.getProductColors(), productFilter.getProductMaterials(),
                        productFilter.getProductOccasions(), productFilter.getProductSeasons(),
                        productFilter.getProductBrandSizes(), productFilter.getProductCustomSizes(),
                        productFilter.getProductDesigners());

                Log.i("FilterOBJ", "onClick: " + JsonUtils.toJson(productFilterObj));
                // TODO make a call to API to bring all products related to filtered parameters
            }
        });

        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.clearFilterTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilterData();
            }
        });
    }

    private void clearFilterData() {
        productFilter = new ProductFilter();
        int current = prevFilterType;
        prevFilterType = 0;
        loadFragment(current);
    }

    private void initFragment() {
        binding.filterByType.setOnClickListener(v -> {
            changeFilterTypeView(TYPE);
            loadFragment(TYPE);
        });
        binding.filterByPrice.setOnClickListener(v -> {
            changeFilterTypeView(PRICE);
            loadFragment(PRICE);
        });
        binding.filterByPattern.setOnClickListener(v -> {
            changeFilterTypeView(PATTERN);
            loadFragment(PATTERN);
        });
        binding.filterByKnitOrWoven.setOnClickListener(v -> {
            changeFilterTypeView(KNIT_WOVEN);
            loadFragment(KNIT_WOVEN);
        });
        binding.filterByWashCare.setOnClickListener(v -> {
            changeFilterTypeView(WASH_CARE);
            loadFragment(WASH_CARE);
        });
        binding.filterByColor.setOnClickListener(v -> {
            changeFilterTypeView(COLOR);
            loadFragment(COLOR);
        });
        binding.filterByMaterial.setOnClickListener(v -> {
            changeFilterTypeView(MATERIAL);
            loadFragment(MATERIAL);
        });
        binding.filterByOccasion.setOnClickListener(v -> {
            changeFilterTypeView(OCCASION);
            loadFragment(OCCASION);
        });
        binding.filterBySeason.setOnClickListener(v -> {
            changeFilterTypeView(SEASON);
            loadFragment(SEASON);
        });
        binding.filterBySize.setOnClickListener(v -> {
            changeFilterTypeView(SIZE);
            loadFragment(SIZE);
        });
        binding.filterByDesigner.setOnClickListener(v -> {
            changeFilterTypeView(DESIGNER);
            loadFragment(DESIGNER);
        });
    }

    private void changeFilterTypeView(int type) {
        binding.filterByType.setBackgroundColor(ProductFilterActivity.this.
                getResources().getColor(type == TYPE ? R.color.primary_dark : R.color.transparent));
        binding.filterByType.setTextColor(getResources().
                getColor(type == TYPE ? R.color.white : R.color.text_primary_black));

        binding.filterByPrice.setBackgroundColor(ProductFilterActivity.this.
                getResources().getColor(type == PRICE ? R.color.primary_dark : R.color.transparent));
        binding.filterByPrice.setTextColor(getResources().
                getColor(type == PRICE ? R.color.white : R.color.text_primary_black));

        binding.filterByPattern.setBackgroundColor(ProductFilterActivity.this.
                getResources().getColor(type == PATTERN ? R.color.primary_dark : R.color.transparent));
        binding.filterByPattern.setTextColor(getResources().
                getColor(type == PATTERN ? R.color.white : R.color.text_primary_black));

        binding.filterByKnitOrWoven.setBackgroundColor(ProductFilterActivity.this.
                getResources().getColor(type == KNIT_WOVEN ? R.color.primary_dark : R.color.transparent));
        binding.filterByKnitOrWoven.setTextColor(getResources().
                getColor(type == KNIT_WOVEN ? R.color.white : R.color.text_primary_black));

        binding.filterByWashCare.setBackgroundColor(ProductFilterActivity.this.
                getResources().getColor(type == WASH_CARE ? R.color.primary_dark : R.color.transparent));
        binding.filterByWashCare.setTextColor(getResources().
                getColor(type == WASH_CARE ? R.color.white : R.color.text_primary_black));

        binding.filterByColor.setBackgroundColor(ProductFilterActivity.this.
                getResources().getColor(type == COLOR ? R.color.primary_dark : R.color.transparent));
        binding.filterByColor.setTextColor(getResources().
                getColor(type == COLOR ? R.color.white : R.color.text_primary_black));

        binding.filterByMaterial.setBackgroundColor(ProductFilterActivity.this.
                getResources().getColor(type == MATERIAL ? R.color.primary_dark : R.color.transparent));
        binding.filterByMaterial.setTextColor(getResources().
                getColor(type == MATERIAL ? R.color.white : R.color.text_primary_black));

        binding.filterByOccasion.setBackgroundColor(ProductFilterActivity.this.
                getResources().getColor(type == OCCASION ? R.color.primary_dark : R.color.transparent));
        binding.filterByOccasion.setTextColor(getResources().
                getColor(type == OCCASION ? R.color.white : R.color.text_primary_black));

        binding.filterBySeason.setBackgroundColor(ProductFilterActivity.this.
                getResources().getColor(type == SEASON ? R.color.primary_dark : R.color.transparent));
        binding.filterBySeason.setTextColor(getResources().
                getColor(type == SEASON ? R.color.white : R.color.text_primary_black));

        binding.filterBySize.setBackgroundColor(ProductFilterActivity.this.
                getResources().getColor(type == SIZE ? R.color.primary_dark : R.color.transparent));
        binding.filterBySize.setTextColor(getResources().
                getColor(type == SIZE ? R.color.white : R.color.text_primary_black));

        binding.filterByDesigner.setBackgroundColor(ProductFilterActivity.this.
                getResources().getColor(type == DESIGNER ? R.color.primary_dark : R.color.transparent));
        binding.filterByDesigner.setTextColor(getResources().
                getColor(type == DESIGNER ? R.color.white : R.color.text_primary_black));
    }

    private void loadFragment(int type) {
        if(type == prevFilterType) { return; }
        switch (type) {
            case TYPE:
                loadFragment(new TypeFragment(productFilter.getProductTypes(),
                        productFilter.getAgeGroup(), productFilter.getGender()), TYPE);
                break;
            case PRICE:
                loadFragment(new PriceFragment(productFilter.getMinPrice(),
                        productFilter.getMaxPrice()), PRICE);
                break;
            case PATTERN:
                loadFragment(new PatternFragment(productFilter.getProductPatterns()), PATTERN);
                break;
            case KNIT_WOVEN:
                loadFragment(new KnitWovenFragment(productFilter.getProductKnitWovens()),
                        KNIT_WOVEN);
                break;
            case WASH_CARE:
                loadFragment(new WashCareFragment(productFilter.getProductWashCares()), WASH_CARE);
                break;
            case COLOR:
                loadFragment(new ColorFragment(productFilter.getProductColors()), COLOR);
                break;
            case MATERIAL:
                loadFragment(new MaterialFragment(productFilter.getProductMaterials()), MATERIAL);
                break;
            case OCCASION:
                loadFragment(new OccasionFragment(productFilter.getProductOccasions()), OCCASION);
                break;
            case SEASON:
                loadFragment(new SeasonFragment(productFilter.getProductSeasons()), SEASON);
                break;
            case SIZE:
                loadFragment(new SizeFragment(productFilter.getProductBrandSizes(),
                        productFilter.getProductCustomSizes()), SIZE);
                break;
            case DESIGNER:
                loadFragment(new DesignerFragment(productFilter.getProductDesigners()), DESIGNER);
                break;
        }
    }

    private FilterFragment prevLoadedFragment;
    private int prevFilterType;

    private void loadFragment(FilterFragment fragment, int filterType) {
        savePrevLoadedFilterData();
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.fragmentContainer.getId(), fragment)
                    .commit();
            prevLoadedFragment = fragment;
            prevFilterType = filterType;
        }
    }

    private void savePrevLoadedFilterData() {
        if (prevLoadedFragment == null || prevFilterType == 0) {
            return;
        }

        ArrayList<Integer> data = prevLoadedFragment.getSelectedData();
        ArrayList<String> brandSizesData = prevLoadedFragment.getSelectedBrandSizes();
        ArrayList<String> customSizesData = prevLoadedFragment.getSelectedCustomSizes();
        float minPrice = prevLoadedFragment.getMinPrice();
        float maxPrice = prevLoadedFragment.getMaxPrice();
        int ageGroup = prevLoadedFragment.getSelectedAgeGroup();
        int gender = prevLoadedFragment.getSelectedGender();

        switch (prevFilterType) {
            case TYPE:
                productFilter.setProductTypes(data);
                productFilter.setAgeGroup(ageGroup);
                productFilter.setGender(gender);
                break;
            case PRICE:
                productFilter.setMinPrice(minPrice);
                productFilter.setMaxPrice(maxPrice);
                break;
            case PATTERN:
                productFilter.setProductPatterns(data);
                break;
            case KNIT_WOVEN:
                productFilter.setProductKnitWovens(data);
                break;
            case WASH_CARE:
                productFilter.setProductWashCares(data);
                break;
            case COLOR:
                productFilter.setProductColors(data);
                break;
            case MATERIAL:
                productFilter.setProductMaterials(data);
                break;
            case OCCASION:
                productFilter.setProductOccasions(data);
                break;
            case SEASON:
                productFilter.setProductSeasons(data);
                break;
            case SIZE:
                productFilter.setProductBrandSizes(brandSizesData);
                productFilter.setProductCustomSizes(customSizesData);
                break;
            case DESIGNER:
                productFilter.setProductDesigners(data);
                break;
        }
    }
}
