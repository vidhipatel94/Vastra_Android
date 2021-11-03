package com.esolution.vastrashopper.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrashopper.databinding.FragmentFilterBinding;

public class PatternFragment extends Fragment {

    private FragmentFilterBinding binding;
    private PatternAdapter patternAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.filterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        initView();

        return root;
    }

    private void initView() {
        patternAdapter = new PatternAdapter();
        binding.filterRecyclerView.setAdapter(patternAdapter);
        patternAdapter.notifyDataSetChanged();
    }
}
