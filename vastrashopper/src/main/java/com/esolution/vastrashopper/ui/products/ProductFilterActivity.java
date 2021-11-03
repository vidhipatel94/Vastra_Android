package com.esolution.vastrashopper.ui.products;

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

        initFragment();
    }

    private void initFragment() {

        binding.filterByType.setOnClickListener(v -> loadFragment(new TypeFragment()));
        binding.filterByPrice.setOnClickListener(v -> loadFragment(new TypeFragment()));
        binding.filterByPattern.setOnClickListener(v -> loadFragment(new TypeFragment()));
        binding.filterByKnitOrWoven.setOnClickListener(v -> loadFragment(new TypeFragment()));
        binding.filterByWashCare.setOnClickListener(v -> loadFragment(new TypeFragment()));
        binding.filterByColor.setOnClickListener(v -> loadFragment(new TypeFragment()));
        binding.filterByMaterial.setOnClickListener(v -> loadFragment(new TypeFragment()));
        binding.filterByOccasion.setOnClickListener(v -> loadFragment(new TypeFragment()));
        binding.filterBySeason.setOnClickListener(v -> loadFragment(new TypeFragment()));
        binding.filterBySize.setOnClickListener(v -> loadFragment(new TypeFragment()));
        binding.filterByDesigner.setOnClickListener(v -> loadFragment(new TypeFragment()));
    }

    private void loadFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction()
            .replace(binding.fragmentContainer.getId(), fragment)
            .commit();
        }
    }
}
