package com.esolution.vastrafashiondesigner.ui.newproduct.addsize;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.esolution.vastrabasic.models.product.ProductSize;
import com.esolution.vastrabasic.utils.Utils;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.FragmentDialogSizeMeasurementBinding;

import org.jetbrains.annotations.NotNull;

public class SizeMeasurementDialog extends DialogFragment {

    private static final String EXTRA_PARAMETER = "extra_parameter";

    public static SizeMeasurementDialog newInstance(String parameter,
                                                    ProductSize productSize,
                                                    Listener listener) {
        SizeMeasurementDialog dialog = new SizeMeasurementDialog(productSize, listener);
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_PARAMETER, parameter);
        dialog.setArguments(bundle);
        return dialog;
    }

    private FragmentDialogSizeMeasurementBinding binding;
    private ProductSize productSize;
    private Listener listener;

    private String parameter = null;

    public SizeMeasurementDialog(ProductSize productSize, Listener listener) {
        this.productSize = productSize;
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            parameter = getArguments().getString(EXTRA_PARAMETER, null);
            if (TextUtils.isEmpty(parameter)) {
                dismiss();
            }
        } else {
            dismiss();
        }

        binding = FragmentDialogSizeMeasurementBinding.inflate(getLayoutInflater());
        binding.title.setText(parameter + " - " + productSize.getSizeText(getContext()));
        initView();
        fillValue();
        AlertDialog alertDialog = new AlertDialog.Builder(requireActivity())
                .setView(binding.getRoot())
                .create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return alertDialog;
    }

    private void initView() {
        changeVisibilityBasedOnParameter();

        binding.radioFixSize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.radioSizeRange.setChecked(false);
                    binding.inputFixSize.setVisibility(View.VISIBLE);
                    binding.inputMinSize.setVisibility(View.GONE);
                    binding.inputMaxSize.setVisibility(View.GONE);
                }
            }
        });
        binding.radioSizeRange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.radioFixSize.setChecked(false);
                    binding.inputFixSize.setVisibility(View.GONE);
                    binding.inputMinSize.setVisibility(View.VISIBLE);
                    binding.inputMaxSize.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.iconClose.setOnClickListener((v) -> dismiss());

        binding.btnDone.setOnClickListener((v) -> {
            if (listener != null) {
                if (isFormDataSaved()) {
                    listener.onMeasurementAdded(productSize);
                    dismiss();
                }
            } else {
                dismiss();
            }
        });
    }

    private void changeVisibilityBasedOnParameter() {
        if (parameter.equals(getString(R.string.label_US_size))) {
            binding.radioUSSize.setVisibility(View.VISIBLE);
            binding.inputUSSize.setVisibility(View.VISIBLE);
            binding.radioFixSize.setVisibility(View.GONE);
            binding.inputFixSize.setVisibility(View.GONE);
            binding.radioSizeRange.setVisibility(View.GONE);
            binding.inputMinSize.setVisibility(View.GONE);
            binding.inputMaxSize.setVisibility(View.GONE);

            binding.radioUSSize.setChecked(true);

        } else if (parameter.equals(getString(R.string.label_front_length)) ||
                parameter.equals(getString(R.string.label_back_length)) ||
                parameter.equals(getString(R.string.label_width)) ||
                parameter.equals(getString(R.string.label_inseam_length)) ||
                parameter.equals(getString(R.string.label_outseam_length)) ||
                parameter.equals(getString(R.string.label_sleeve_length)) ||
                parameter.equals(getString(R.string.label_foot_length))) {
            binding.radioSizeRange.setVisibility(View.GONE);
            binding.inputMinSize.setVisibility(View.GONE);
            binding.inputMaxSize.setVisibility(View.GONE);
            binding.radioFixSize.setChecked(true);
            binding.inputFixSize.setVisibility(View.VISIBLE);
        }
    }

    private void fillValue() {
        if (parameter.equals(getString(R.string.label_US_size))) {
            binding.inputUSSize.setText(productSize.getUSSize());
            return;
        }
        float size = 0;
        if (parameter.equals(getString(R.string.label_front_length))) {
            size = productSize.getFrontLength();
        } else if (parameter.equals(getString(R.string.label_back_length))) {
            size = productSize.getBackLength();
        } else if (parameter.equals(getString(R.string.label_width))) {
            size = productSize.getWidth();
        } else if (parameter.equals(getString(R.string.label_inseam_length))) {
            size = productSize.getInseamLength();
        } else if (parameter.equals(getString(R.string.label_outseam_length))) {
            size = productSize.getOutseamLength();
        } else if (parameter.equals(getString(R.string.label_sleeve_length))) {
            size = productSize.getSleeveLength();
        } else if (parameter.equals(getString(R.string.label_foot_length))) {
            size = productSize.getFootLength();
        }
        if (size > 0) {
            binding.radioFixSize.setChecked(true);
            binding.inputFixSize.setText(String.valueOf(size));
            return;
        }

        float minSize = 0;
        float maxSize = 0;
        if (parameter.equals(getString(R.string.label_head_circumference))) {
            minSize = productSize.getHeadCircumferenceMin();
            maxSize = productSize.getHeadCircumferenceMax();
        } else if (parameter.equals(getString(R.string.label_neck))) {
            minSize = productSize.getNeckMin();
            maxSize = productSize.getNeckMax();
        } else if (parameter.equals(getString(R.string.label_bust))) {
            minSize = productSize.getBustMin();
            maxSize = productSize.getBustMax();
        } else if (parameter.equals(getString(R.string.label_waist))) {
            minSize = productSize.getWaistMin();
            maxSize = productSize.getWaistMax();
        } else if (parameter.equals(getString(R.string.label_hip))) {
            minSize = productSize.getHipMin();
            maxSize = productSize.getHipMax();
        } else if (parameter.equals(getString(R.string.label_wrist))) {
            minSize = productSize.getWristMin();
            maxSize = productSize.getWristMax();
        }
        if (minSize > 0 && maxSize > 0 && minSize != maxSize) {
            binding.radioSizeRange.setChecked(true);
            binding.inputMinSize.setText(String.valueOf(minSize));
            binding.inputMaxSize.setText(String.valueOf(maxSize));
        } else {
            size = Math.max(minSize, maxSize);
            if (size > 0) {
                binding.radioFixSize.setChecked(true);
                binding.inputFixSize.setText(String.valueOf(size));
            }
        }
    }

    private boolean isFormDataSaved() {
        if (binding.radioUSSize.isChecked()) {
            String size = binding.inputUSSize.getText().toString().trim();
            saveUSSize(size);

        } else if (binding.radioFixSize.isChecked()) {
            String sizeStr = binding.inputFixSize.getText().toString().trim();
            float size = 0;
            if (!sizeStr.isEmpty()) {
                try {
                    size = Float.parseFloat(sizeStr);
                } catch (NumberFormatException e) {
                    Utils.showMessage(binding.getRoot(), getString(R.string.error_invalid_size));
                    return false;
                }
            }
            if (size >= 100) {
                Utils.showMessage(binding.getRoot(), getString(R.string.error_invalid_size));
                return false;
            }
            saveSize(size, size);

        } else if (binding.radioSizeRange.isChecked()) {
            String minSizeStr = binding.inputMinSize.getText().toString().trim();
            float minSize = 0;
            if (!minSizeStr.isEmpty()) {
                try {
                    minSize = Float.parseFloat(minSizeStr);
                } catch (NumberFormatException e) {
                    Utils.showMessage(binding.getRoot(), getString(R.string.error_invalid_min_size));
                    return false;
                }
            }
            if (minSize >= 100) {
                Utils.showMessage(binding.getRoot(), getString(R.string.error_invalid_min_size));
                return false;
            }

            String maxSizeStr = binding.inputMaxSize.getText().toString().trim();
            float maxSize = 0;
            if (!maxSizeStr.isEmpty()) {
                try {
                    maxSize = Float.parseFloat(maxSizeStr);
                } catch (NumberFormatException e) {
                    Utils.showMessage(binding.getRoot(), getString(R.string.error_invalid_max_size));
                    return false;
                }
            }
            if (maxSize >= 100) {
                Utils.showMessage(binding.getRoot(), getString(R.string.error_invalid_max_size));
                return false;
            }

            if (minSize != 0 && maxSize == 0) {
                maxSize = minSize;
            } else if (minSize == 0 && maxSize != 0) {
                minSize = maxSize;
            } else if (minSize != 0 && maxSize != 0 && minSize > maxSize) {
                Utils.showMessage(binding.getRoot(), getString(R.string.error_size_range));
                return false;
            }
            saveSize(minSize, maxSize);
        }
        return true;
    }

    private void saveUSSize(String size) {
        if (parameter.equals(getString(R.string.label_US_size))) {
            productSize.setUSSize(size);
        }
    }

    private void saveSize(float minSize, float maxSize) {
        if (parameter.equals(getString(R.string.label_front_length))) {
            productSize.setFrontLength(minSize);
        } else if (parameter.equals(getString(R.string.label_back_length))) {
            productSize.setBackLength(minSize);
        } else if (parameter.equals(getString(R.string.label_width))) {
            productSize.setWidth(minSize);
        } else if (parameter.equals(getString(R.string.label_head_circumference))) {
            productSize.setHeadCircumferenceMin(minSize);
            productSize.setHeadCircumferenceMax(maxSize);
        } else if (parameter.equals(getString(R.string.label_neck))) {
            productSize.setNeckMin(minSize);
            productSize.setNeckMax(maxSize);
        } else if (parameter.equals(getString(R.string.label_bust))) {
            productSize.setBustMin(minSize);
            productSize.setBustMax(maxSize);
        } else if (parameter.equals(getString(R.string.label_waist))) {
            productSize.setWaistMin(minSize);
            productSize.setWaistMax(maxSize);
        } else if (parameter.equals(getString(R.string.label_hip))) {
            productSize.setHipMin(minSize);
            productSize.setHipMax(maxSize);
        } else if (parameter.equals(getString(R.string.label_inseam_length))) {
            productSize.setInseamLength(minSize);
        } else if (parameter.equals(getString(R.string.label_outseam_length))) {
            productSize.setOutseamLength(minSize);
        } else if (parameter.equals(getString(R.string.label_sleeve_length))) {
            productSize.setSleeveLength(minSize);
        } else if (parameter.equals(getString(R.string.label_wrist))) {
            productSize.setWristMin(minSize);
            productSize.setWristMax(maxSize);
        } else if (parameter.equals(getString(R.string.label_foot_length))) {
            productSize.setFootLength(minSize);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    interface Listener {
        void onMeasurementAdded(ProductSize productSize);
    }
}
