package com.esolution.vastrashopper.ui.designers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrashopper.databinding.FragmentDesignersBinding;

public class DesignersFragment extends Fragment {

    private DesignersViewModel designersViewModel;
    private FragmentDesignersBinding binding;
    private DesignersAdapter designersAdapter;
    private RecyclerView designerRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        designersViewModel =
                new ViewModelProvider(this).get(DesignersViewModel.class);

        binding = FragmentDesignersBinding.inflate(inflater, container, false);

        initView();
        bindRecyclerViewAdapter();

        return binding.getRoot();
    }

    private void initView() {
        designerRecyclerView = binding.designerRecyclerView;
        designersAdapter = new DesignersAdapter();
    }

    private void bindRecyclerViewAdapter() {
        designerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        designerRecyclerView.setAdapter(designersAdapter);
        designersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}