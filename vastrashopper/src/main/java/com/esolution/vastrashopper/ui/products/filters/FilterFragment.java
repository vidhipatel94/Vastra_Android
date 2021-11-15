package com.esolution.vastrashopper.ui.products.filters;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FilterFragment extends Fragment {
    protected ArrayList<Integer> getSelectedData() { return null; }

    protected float getMinPrice() { return 0f; }

    protected float getMaxPrice() {
        return 10000f;
    }

    protected ArrayList<String> getSelectedBrandSizes() { return null; }

    protected ArrayList<String> getSelectedCustomSizes() { return null; }

    protected int getSelectedGender() { return -1; }

    protected int getSelectedAgeGroup() { return -1; }
}
