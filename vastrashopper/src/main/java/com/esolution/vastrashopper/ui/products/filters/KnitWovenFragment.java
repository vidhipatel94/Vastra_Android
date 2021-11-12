package com.esolution.vastrashopper.ui.products.filters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.databinding.FragmentFilterBinding;

import java.util.ArrayList;

public class KnitWovenFragment extends FilterFragment {

    private FragmentFilterBinding binding;
    private KnitWovenAdapter knitWovenAdapter;

    private static String[] KNIT_WOVEN;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFilterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        KNIT_WOVEN = getResources().getStringArray(R.array.knit_wovens);

        initView();

        return root;
    }

    private void initView() {
        binding.filterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        knitWovenAdapter = new KnitWovenAdapter(KNIT_WOVEN);
        binding.filterRecyclerView.setAdapter(knitWovenAdapter);
        knitWovenAdapter.notifyDataSetChanged();
    }

    @Override
    protected ArrayList<Integer> getSelectedData() {
        if(knitWovenAdapter==null) return null;
        return knitWovenAdapter.getSelectedKnitWovens();
    }
}
