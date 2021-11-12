package com.esolution.vastrashopper.ui.products.filters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.esolution.vastrashopper.databinding.FragmentPriceBinding;
import com.google.android.material.slider.RangeSlider;

import org.jetbrains.annotations.NotNull;

public class PriceFragment extends FilterFragment {

    private FragmentPriceBinding binding;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPriceBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        initView();

        binding.priceRangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull @NotNull RangeSlider slider, float value, boolean fromUser) {
                binding.minPriceRange.setText(String.valueOf(slider.getValues().get(0)));
                binding.maxPriceRange.setText(String.valueOf(slider.getValues().get(1)));
            }
        });

        return root;
    }

    private void initView() {
        binding.minPriceRange.setText(String.valueOf(binding.priceRangeSlider.getValueFrom()));
        binding.maxPriceRange.setText(String.valueOf(binding.priceRangeSlider.getValueTo()));
    }

    @Override
    protected float getMinPrice() {
        return Float.parseFloat(binding.minPriceRange.getText().toString().trim());
    }

    @Override
    protected float getMaxPrice() {
        return Float.parseFloat(binding.maxPriceRange.getText().toString().trim());
    }
}
