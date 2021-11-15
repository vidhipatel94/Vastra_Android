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

public class SeasonFragment extends FilterFragment {

    private FragmentFilterBinding binding;
    private SeasonAdapter seasonAdapter;
    private final List<Integer> prevSelectedSeasons;

    private static String[] SEASONS;

    public SeasonFragment(List<Integer> prevSelectedSeasons) {
        this.prevSelectedSeasons = prevSelectedSeasons;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SEASONS = getResources().getStringArray(R.array.seasons);

        initView();

        return root;
    }

    private void initView() {
        binding.filterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        seasonAdapter = new SeasonAdapter(SEASONS, prevSelectedSeasons);
        binding.filterRecyclerView.setAdapter(seasonAdapter);
        seasonAdapter.notifyDataSetChanged();
    }

    @Override
    protected ArrayList<Integer> getSelectedData() {
        if(seasonAdapter==null) return null;
        return seasonAdapter.getSelectedSeasons();
    }
}

