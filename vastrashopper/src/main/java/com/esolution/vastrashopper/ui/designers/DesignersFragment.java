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

import com.esolution.vastrashopper.databinding.FragmentDesignersBinding;

public class DesignersFragment extends Fragment {

    private DesignersViewModel designersViewModel;
    private FragmentDesignersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        designersViewModel =
                new ViewModelProvider(this).get(DesignersViewModel.class);

        binding = FragmentDesignersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDesigners;
        designersViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}