package com.esolution.vastrashopper.ui.products.filters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.esolution.vastrashopper.databinding.ActivityProductFiltersBinding;

public class ProductFilterActivity extends AppCompatActivity {

    private ActivityProductFiltersBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductFiltersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadFragment(new TypeFragment());
        initFragment();
    }

    private void initFragment() {

        binding.filterByType.setOnClickListener(v -> loadFragment(new TypeFragment()));
        binding.filterByPrice.setOnClickListener(v -> loadFragment(new PriceFragment()));
        binding.filterByPattern.setOnClickListener(v -> loadFragment(new PatternFragment()));
        binding.filterByKnitOrWoven.setOnClickListener(v -> loadFragment(new KnitWovenFragment()));
        binding.filterByWashCare.setOnClickListener(v -> loadFragment(new WashCareFragment()));
        binding.filterByColor.setOnClickListener(v -> loadFragment(new ColorFragment()));
        binding.filterByMaterial.setOnClickListener(v -> loadFragment(new MaterialFragment()));
        binding.filterByOccasion.setOnClickListener(v -> loadFragment(new OccasionFragment()));
        binding.filterBySeason.setOnClickListener(v -> loadFragment(new SeasonFragment()));
        binding.filterBySize.setOnClickListener(v -> loadFragment(new SizeFragment()));
        binding.filterByDesigner.setOnClickListener(v -> loadFragment(new DesignerFragment()));
    }

    private void loadFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction()
            .replace(binding.fragmentContainer.getId(), fragment)
            .commit();
        }
    }
}
