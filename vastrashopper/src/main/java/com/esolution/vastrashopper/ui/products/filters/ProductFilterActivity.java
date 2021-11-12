package com.esolution.vastrashopper.ui.products.filters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.esolution.vastrabasic.models.ProductFilter;
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

        loadFragment(new TypeFragment(),TYPE);
        initFragment();
    }

    private void initFragment() {
        binding.filterByType.setOnClickListener(v -> loadFragment(new TypeFragment(),TYPE));
        binding.filterByPrice.setOnClickListener(v -> loadFragment(new PriceFragment(), PRICE));
        binding.filterByPattern.setOnClickListener(v -> loadFragment(new PatternFragment(), PATTERN));
        binding.filterByKnitOrWoven.setOnClickListener(v -> loadFragment(new KnitWovenFragment(), KNIT_WOVEN));
        binding.filterByWashCare.setOnClickListener(v -> loadFragment(new WashCareFragment(),WASH_CARE));
        binding.filterByColor.setOnClickListener(v -> loadFragment(new ColorFragment(), COLOR));
        binding.filterByMaterial.setOnClickListener(v -> loadFragment(new MaterialFragment(), MATERIAL));
        binding.filterByOccasion.setOnClickListener(v -> loadFragment(new OccasionFragment(), OCCASION));
        binding.filterBySeason.setOnClickListener(v -> loadFragment(new SeasonFragment(), SEASON));
        binding.filterBySize.setOnClickListener(v -> loadFragment(new SizeFragment(), SIZE));
        binding.filterByDesigner.setOnClickListener(v -> loadFragment(new DesignerFragment(), DESIGNER));
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

        switch (prevFilterType) {
            case TYPE:
                productFilter.setProductTypes(data);
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
