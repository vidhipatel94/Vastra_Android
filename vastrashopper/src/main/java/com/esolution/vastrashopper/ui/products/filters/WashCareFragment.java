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
import java.util.List;

public class WashCareFragment extends FilterFragment {

    private FragmentFilterBinding binding;
    private WashCareAdapter washCareAdapter;
    private final List<Integer> prevSelectedWashCares;

    private static String[] WASHCARES;

    public WashCareFragment(List<Integer> prevSelectedWashCares) {
        this.prevSelectedWashCares = prevSelectedWashCares;
    }

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
        washCareAdapter = new WashCareAdapter(WASHCARES, prevSelectedWashCares);
        binding.filterRecyclerView.setAdapter(washCareAdapter);
        washCareAdapter.notifyDataSetChanged();
    }

    @Override
    protected ArrayList<Integer> getSelectedData() {
        if(washCareAdapter==null) return null;
        return washCareAdapter.getSelectedWashCares();
    }
}

