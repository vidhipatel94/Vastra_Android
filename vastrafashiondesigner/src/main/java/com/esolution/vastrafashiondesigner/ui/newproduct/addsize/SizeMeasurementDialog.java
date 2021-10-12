package com.esolution.vastrafashiondesigner.ui.newproduct.addsize;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.esolution.vastrafashiondesigner.databinding.FragmentDialogSizeMeasurementBinding;

import org.jetbrains.annotations.NotNull;

public class SizeMeasurementDialog extends DialogFragment {

    private static final String EXTRA_PARAMETER = "extra_parameter";
    private static final String EXTRA_SIZE = "extra_size";

    public static SizeMeasurementDialog newInstance(String parameter, String size) {
        SizeMeasurementDialog dialog = new SizeMeasurementDialog();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_PARAMETER, parameter);
        bundle.putString(EXTRA_SIZE, size);
        dialog.setArguments(bundle);
        return dialog;
    }

    private FragmentDialogSizeMeasurementBinding binding;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        String parameter = null;
        String size = null;
        if (getArguments() != null) {
            parameter = getArguments().getString(EXTRA_PARAMETER, null);
            size = getArguments().getString(EXTRA_SIZE, null);
        } else {
            dismiss();
        }

        binding = FragmentDialogSizeMeasurementBinding.inflate(getLayoutInflater());
        binding.title.setText(parameter + " - " + size);
        initView();
        return new AlertDialog.Builder(requireActivity())
                .setView(binding.getRoot())
                .create();
    }

    private void initView() {
        binding.radioFixSize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.radioSizeRange.setChecked(false);
                binding.inputFixSize.setVisibility(View.VISIBLE);
                binding.inputMinSize.setVisibility(View.GONE);
                binding.inputMaxSize.setVisibility(View.GONE);
            }
        });
        binding.radioSizeRange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.radioFixSize.setChecked(false);
                binding.inputFixSize.setVisibility(View.GONE);
                binding.inputMinSize.setVisibility(View.VISIBLE);
                binding.inputMaxSize.setVisibility(View.VISIBLE);
            }
        });
        binding.iconClose.setOnClickListener((v) -> dismiss());

        binding.btnDone.setOnClickListener((v) -> {
            dismiss();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
