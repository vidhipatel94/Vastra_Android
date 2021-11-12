package com.esolution.vastrashopper.ui.products.filters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.R;
import com.esolution.vastrashopper.databinding.FragmentFilterBinding;

import java.util.ArrayList;

public class WashCareFragment extends FilterFragment {

    private FragmentFilterBinding binding;
    private WashCareAdapter washCareAdapter;

    private static String[] WASHCARES;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        WASHCARES = getResources().getStringArray(R.array.wash_cares);

        initView();

        return root;
    }

    private void initView() {
        binding.filterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        washCareAdapter = new WashCareAdapter(WASHCARES);
        binding.filterRecyclerView.setAdapter(washCareAdapter);
        washCareAdapter.notifyDataSetChanged();
    }

    @Override
    protected ArrayList<Integer> getSelectedData() {
        if(washCareAdapter==null) return null;
        return washCareAdapter.getSelectedWashCares();
    }
}

