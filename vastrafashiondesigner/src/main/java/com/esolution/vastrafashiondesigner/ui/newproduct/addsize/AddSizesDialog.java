package com.esolution.vastrafashiondesigner.ui.newproduct.addsize;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.FragmentDialogAddSizesBinding;
import com.esolution.vastrafashiondesigner.databinding.RowAddProductSizeBinding;

import org.jetbrains.annotations.NotNull;

public class AddSizesDialog extends DialogFragment {

    private FragmentDialogAddSizesBinding binding;

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentDialogAddSizesBinding.inflate(getLayoutInflater());
        initView();
        AlertDialog alertDialog = new AlertDialog.Builder(requireActivity())
                .setView(binding.getRoot())
                .create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return alertDialog;
    }

    private void initView() {
        binding.iconClose.setOnClickListener((v) -> dismiss());

        setAllRadioButtonsUI();

        setSizeByWeightLayout();
        setSizeByVolumeLayout();
        setCustomSizeLayout();

        binding.btnDone.setOnClickListener((v) -> {
            dismiss();
        });
    }

    private void setAllRadioButtonsUI() {
        binding.radioOneSize.setOnCheckedChangeListener(onCheckedChangeListener);
        binding.radioLetterSize.setOnCheckedChangeListener(onCheckedChangeListener);
        binding.radioSizeByWeight.setOnCheckedChangeListener(onCheckedChangeListener);
        binding.radioSizeByVolume.setOnCheckedChangeListener(onCheckedChangeListener);
        binding.radioCustomSize.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    private final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView.getId() == R.id.radioOneSize) {
                    binding.radioLetterSize.setChecked(false);
                    binding.radioSizeByWeight.setChecked(false);
                    binding.radioSizeByVolume.setChecked(false);
                    binding.radioCustomSize.setChecked(false);

                    binding.letterSizeOptionsLayout.setVisibility(View.GONE);

                    binding.layoutSizeByWeight1.rootLayout.setVisibility(View.GONE);
                    binding.layoutAddSizeByWeight.setVisibility(View.GONE);
                    binding.btnAddMoreSizeWeight.setVisibility(View.GONE);

                    binding.layoutSizeByVolume1.rootLayout.setVisibility(View.GONE);
                    binding.layoutAddSizeByVolume.setVisibility(View.GONE);
                    binding.btnAddMoreSizeVolume.setVisibility(View.GONE);

                    binding.layoutCustomSize1.rootLayout.setVisibility(View.GONE);
                    binding.layoutAddCustomSize.setVisibility(View.GONE);
                    binding.btnAddMoreCustomSize.setVisibility(View.GONE);

                } else if (buttonView.getId() == R.id.radioLetterSize) {
                    binding.radioOneSize.setChecked(false);
                    binding.radioSizeByWeight.setChecked(false);
                    binding.radioSizeByVolume.setChecked(false);
                    binding.radioCustomSize.setChecked(false);

                    binding.letterSizeOptionsLayout.setVisibility(View.VISIBLE);

                    binding.layoutSizeByWeight1.rootLayout.setVisibility(View.GONE);
                    binding.layoutAddSizeByWeight.setVisibility(View.GONE);
                    binding.btnAddMoreSizeWeight.setVisibility(View.GONE);

                    binding.layoutSizeByVolume1.rootLayout.setVisibility(View.GONE);
                    binding.layoutAddSizeByVolume.setVisibility(View.GONE);
                    binding.btnAddMoreSizeVolume.setVisibility(View.GONE);

                    binding.layoutCustomSize1.rootLayout.setVisibility(View.GONE);
                    binding.layoutAddCustomSize.setVisibility(View.GONE);
                    binding.btnAddMoreCustomSize.setVisibility(View.GONE);

                } else if (buttonView.getId() == R.id.radioSizeByWeight) {
                    binding.radioOneSize.setChecked(false);
                    binding.radioLetterSize.setChecked(false);
                    binding.radioSizeByVolume.setChecked(false);
                    binding.radioCustomSize.setChecked(false);

                    binding.letterSizeOptionsLayout.setVisibility(View.GONE);

                    binding.layoutSizeByWeight1.rootLayout.setVisibility(View.VISIBLE);
                    binding.layoutAddSizeByWeight.setVisibility(View.VISIBLE);
                    binding.btnAddMoreSizeWeight.setVisibility(View.VISIBLE);

                    binding.layoutSizeByVolume1.rootLayout.setVisibility(View.GONE);
                    binding.layoutAddSizeByVolume.setVisibility(View.GONE);
                    binding.btnAddMoreSizeVolume.setVisibility(View.GONE);

                    binding.layoutCustomSize1.rootLayout.setVisibility(View.GONE);
                    binding.layoutAddCustomSize.setVisibility(View.GONE);
                    binding.btnAddMoreCustomSize.setVisibility(View.GONE);

                } else if (buttonView.getId() == R.id.radioSizeByVolume) {
                    binding.radioCustomSize.setChecked(false);
                    binding.radioLetterSize.setChecked(false);
                    binding.radioSizeByWeight.setChecked(false);
                    binding.radioCustomSize.setChecked(false);

                    binding.letterSizeOptionsLayout.setVisibility(View.GONE);

                    binding.layoutSizeByWeight1.rootLayout.setVisibility(View.GONE);
                    binding.layoutAddSizeByWeight.setVisibility(View.GONE);
                    binding.btnAddMoreSizeWeight.setVisibility(View.GONE);

                    binding.layoutSizeByVolume1.rootLayout.setVisibility(View.VISIBLE);
                    binding.layoutAddSizeByVolume.setVisibility(View.VISIBLE);
                    binding.btnAddMoreSizeVolume.setVisibility(View.VISIBLE);

                    binding.layoutCustomSize1.rootLayout.setVisibility(View.GONE);
                    binding.layoutAddCustomSize.setVisibility(View.GONE);
                    binding.btnAddMoreCustomSize.setVisibility(View.GONE);

                } else if (buttonView.getId() == R.id.radioCustomSize) {
                    binding.radioOneSize.setChecked(false);
                    binding.radioLetterSize.setChecked(false);
                    binding.radioSizeByWeight.setChecked(false);
                    binding.radioSizeByVolume.setChecked(false);

                    binding.letterSizeOptionsLayout.setVisibility(View.GONE);

                    binding.layoutSizeByWeight1.rootLayout.setVisibility(View.GONE);
                    binding.layoutAddSizeByWeight.setVisibility(View.GONE);
                    binding.btnAddMoreSizeWeight.setVisibility(View.GONE);

                    binding.layoutSizeByVolume1.rootLayout.setVisibility(View.GONE);
                    binding.layoutAddSizeByVolume.setVisibility(View.GONE);
                    binding.btnAddMoreSizeVolume.setVisibility(View.GONE);

                    binding.layoutCustomSize1.rootLayout.setVisibility(View.VISIBLE);
                    binding.layoutAddCustomSize.setVisibility(View.VISIBLE);
                    binding.btnAddMoreCustomSize.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    private void setSizeByWeightLayout() {
        binding.layoutSizeByWeight1.iconDelete.setVisibility(View.INVISIBLE);
        binding.layoutSizeByWeight1.inputBox.setHint(R.string.hint_weight);

        binding.btnAddMoreSizeWeight.setOnClickListener((v) -> {
            RowAddProductSizeBinding sizeBinding = RowAddProductSizeBinding.inflate(getLayoutInflater());
            binding.layoutAddSizeByWeight.addView(sizeBinding.getRoot());

            sizeBinding.inputBox.setHint(R.string.hint_weight);
            sizeBinding.iconDelete.setOnClickListener((v1) -> {
                binding.layoutAddSizeByWeight.removeView(sizeBinding.getRoot());
            });
        });
    }

    private void setSizeByVolumeLayout() {
        binding.layoutSizeByVolume1.iconDelete.setVisibility(View.INVISIBLE);
        binding.layoutSizeByVolume1.inputBox.setHint(R.string.hint_volume);

        binding.btnAddMoreSizeVolume.setOnClickListener((v) -> {
            RowAddProductSizeBinding sizeBinding = RowAddProductSizeBinding.inflate(getLayoutInflater());
            binding.layoutAddSizeByVolume.addView(sizeBinding.getRoot());

            sizeBinding.inputBox.setHint(R.string.hint_volume);
            sizeBinding.iconDelete.setOnClickListener((v1) -> {
                binding.layoutAddSizeByVolume.removeView(sizeBinding.getRoot());
            });
        });
    }

    private void setCustomSizeLayout() {
        binding.layoutCustomSize1.iconDelete.setVisibility(View.INVISIBLE);
        binding.layoutCustomSize1.inputBox.setHint(R.string.hint_custom_size);

        binding.btnAddMoreCustomSize.setOnClickListener((v) -> {
            RowAddProductSizeBinding sizeBinding = RowAddProductSizeBinding.inflate(getLayoutInflater());
            binding.layoutAddCustomSize.addView(sizeBinding.getRoot());

            sizeBinding.inputBox.setHint(R.string.hint_custom_size);
            sizeBinding.iconDelete.setOnClickListener((v1) -> {
                binding.layoutAddCustomSize.removeView(sizeBinding.getRoot());
            });
        });
    }
}
