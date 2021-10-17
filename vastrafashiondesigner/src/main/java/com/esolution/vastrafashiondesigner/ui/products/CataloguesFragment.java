package com.esolution.vastrafashiondesigner.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.esolution.vastrafashiondesigner.databinding.FragmentCataloguesBinding;
import com.esolution.vastrafashiondesigner.databinding.RowCatalogueBinding;
import com.esolution.vastrafashiondesigner.ui.startup.RegisterCreateCatalogueActivity;

public class CataloguesFragment extends Fragment {

    private CataloguesViewModel cataloguesViewModel;
    private FragmentCataloguesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cataloguesViewModel =
                new ViewModelProvider(this).get(CataloguesViewModel.class);

        binding = FragmentCataloguesBinding.inflate(inflater, container, false);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        int totalCatalogues = 4;

        binding.cataloguesLayout.removeAllViews();
        for (int i = 0; i < totalCatalogues; i++) {
            RowCatalogueBinding catalogueBinding = RowCatalogueBinding.inflate(getLayoutInflater());
            binding.cataloguesLayout.addView(catalogueBinding.getRoot());

            catalogueBinding.linkShowAll.setOnClickListener((v) -> {
                Intent intent = new Intent(getContext(), CatalogueProductsActivity.class);
                startActivity(intent);
            });
        }

        binding.btnAdd.setOnClickListener((v) -> {
            Intent intent = new Intent(getContext(), RegisterCreateCatalogueActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}