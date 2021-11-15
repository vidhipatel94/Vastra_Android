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

public class PatternFragment extends FilterFragment {

    private FragmentFilterBinding binding;
    private PatternAdapter patternAdapter;

    private static String[] PATTERNS;
    private final List<Integer> prevSelectedPatterns;

    public PatternFragment(List<Integer> prevSelectedPatterns) {
        this.prevSelectedPatterns = prevSelectedPatterns;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        PATTERNS = getResources().getStringArray(R.array.patterns);

        initView();

        return root;
    }

    private void initView() {
        binding.filterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        patternAdapter = new PatternAdapter(PATTERNS, prevSelectedPatterns);
        binding.filterRecyclerView.setAdapter(patternAdapter);
        patternAdapter.notifyDataSetChanged();
    }

    @Override
    protected ArrayList<Integer> getSelectedData() {
        if (patternAdapter == null) return null;
        return patternAdapter.getSelectedPatterns();
    }
}
