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

public class OccasionFragment extends FilterFragment {

    private FragmentFilterBinding binding;
    private OccasionAdapter occasionAdapter;

    private static String[] OCCASIONS;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        OCCASIONS = getResources().getStringArray(R.array.occasions);

        initView();

        return root;
    }

    private void initView() {
        binding.filterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        occasionAdapter = new OccasionAdapter(OCCASIONS);
        binding.filterRecyclerView.setAdapter(occasionAdapter);
        occasionAdapter.notifyDataSetChanged();
    }

    @Override
    protected ArrayList<Integer> getSelectedData() {
        if(occasionAdapter==null) return null;
        return occasionAdapter.getSelectedOccasions();
    }
}

