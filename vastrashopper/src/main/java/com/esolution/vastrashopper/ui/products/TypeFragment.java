package com.esolution.vastrashopper.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.esolution.vastrashopper.databinding.FragmentTypeBinding;

public class TypeFragment extends Fragment {

    private FragmentTypeBinding binding;
    private TypeAdapter typeAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTypeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.filterTypeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        initView();

        return root;
    }

    private void initView() {
        typeAdapter = new TypeAdapter();
        binding.filterTypeRecyclerView.setAdapter(typeAdapter);
        typeAdapter.notifyDataSetChanged();
    }
}
