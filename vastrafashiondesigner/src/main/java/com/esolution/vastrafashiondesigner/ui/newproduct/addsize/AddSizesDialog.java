package com.esolution.vastrafashiondesigner.ui.newproduct.addsize;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.esolution.vastrabasic.models.product.ProductSize;
import com.esolution.vastrabasic.utils.Utils;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.FragmentDialogAddSizesBinding;
import com.esolution.vastrafashiondesigner.databinding.RowAddProductSizeBinding;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AddSizesDialog extends DialogFragment {

    interface Listener {
        void onSizeIsSelected(ArrayList<ProductSize> productSizes);
    }

    private FragmentDialogAddSizesBinding binding;
    private int productId;
    private Listener listener;

    private ArrayList<ProductSize> productSizes = new ArrayList<>();

    public AddSizesDialog(int productId, ArrayList<ProductSize> productSizes, Listener listener) {
        this.productId = productId;
        if (productSizes != null) {
            this.productSizes.addAll(productSizes);
        }
        this.listener = listener;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentDialogAddSizesBinding.inflate(getLayoutInflater());
        initView();
        fillData();
        AlertDialog alertDialog = new AlertDialog.Builder(requireActivity())
                .setView(binding.getRoot())
                .create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return alertDialog;
    }

    private void initView() {
        binding.iconClose.setOnClickListener((v) -> dismiss());

        setAllRadioButtonsUI();

        setCustomSizeLayout();

        binding.btnDone.setOnClickListener((v) -> {
            Utils.closeKeyboard(v);
            if (isFormValidated()) {
                if (listener != null) {
                    listener.onSizeIsSelected(productSizes);
                }
                dismiss();
            }
        });
    }

    private void setAllRadioButtonsUI() {
        binding.radioOneSize.setOnCheckedChangeListener(onCheckedChangeListener);
        binding.radioLetterSize.setOnCheckedChangeListener(onCheckedChangeListener);
        binding.radioCustomSize.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    private final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView.getId() == R.id.radioOneSize) {
                    binding.radioLetterSize.setChecked(false);
                    binding.radioCustomSize.setChecked(false);

                    binding.letterSizeOptionsLayout.setVisibility(View.GONE);

                    binding.layoutCustomSize1.rootLayout.setVisibility(View.GONE);
                    binding.layoutAddCustomSize.setVisibility(View.GONE);
                    binding.btnAddMoreCustomSize.setVisibility(View.GONE);

                } else if (buttonView.getId() == R.id.radioLetterSize) {
                    binding.radioOneSize.setChecked(false);
                    binding.radioCustomSize.setChecked(false);

                    binding.letterSizeOptionsLayout.setVisibility(View.VISIBLE);

                    binding.layoutCustomSize1.rootLayout.setVisibility(View.GONE);
                    binding.layoutAddCustomSize.setVisibility(View.GONE);
                    binding.btnAddMoreCustomSize.setVisibility(View.GONE);

                } else if (buttonView.getId() == R.id.radioCustomSize) {
                    binding.radioOneSize.setChecked(false);
                    binding.radioLetterSize.setChecked(false);

                    binding.letterSizeOptionsLayout.setVisibility(View.GONE);

                    binding.layoutCustomSize1.rootLayout.setVisibility(View.VISIBLE);
                    binding.layoutAddCustomSize.setVisibility(View.VISIBLE);
                    binding.btnAddMoreCustomSize.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    private List<RowAddProductSizeBinding> customSizeBindings = new ArrayList<>();

    private void setCustomSizeLayout() {
        binding.layoutCustomSize1.iconDelete.setVisibility(View.INVISIBLE);
        binding.layoutCustomSize1.inputBox.setHint(R.string.hint_custom_size);
        customSizeBindings.add(binding.layoutCustomSize1);

        binding.btnAddMoreCustomSize.setOnClickListener((v) -> {
            RowAddProductSizeBinding sizeBinding = RowAddProductSizeBinding.inflate(getLayoutInflater());
            binding.layoutAddCustomSize.addView(sizeBinding.getRoot());
            customSizeBindings.add(sizeBinding);

            sizeBinding.inputBox.setHint(R.string.hint_custom_size);
            sizeBinding.iconDelete.setOnClickListener((v1) -> {
                binding.layoutAddCustomSize.removeView(sizeBinding.getRoot());
                customSizeBindings.remove(sizeBinding);
            });
        });
    }

    private void fillData() {
        if (productSizes.isEmpty()) {
            return;
        }

        for (int i = 0; i < productSizes.size(); i++) {
            ProductSize productSize = productSizes.get(i);
            if (productSize.getSizeType() == ProductSize.TYPE_ONE_SIZE) {
                binding.radioOneSize.setChecked(true);
            } else if (productSize.getSizeType() == ProductSize.TYPE_LETTER_SIZE) {
                binding.radioLetterSize.setChecked(true);
                String size = productSize.getBrandSize();
                if (size.equals(getString(R.string.size_xs))) {
                    binding.cbSizeXS.setChecked(true);
                } else if (size.equals(getString(R.string.size_s))) {
                    binding.cbSizeS.setChecked(true);
                } else if (size.equals(getString(R.string.size_m))) {
                    binding.cbSizeM.setChecked(true);
                } else if (size.equals(getString(R.string.size_l))) {
                    binding.cbSizeL.setChecked(true);
                } else if (size.equals(getString(R.string.size_xl))) {
                    binding.cbSizeXL.setChecked(true);
                } else if (size.equals(getString(R.string.size_2xl))) {
                    binding.cbSize2XL.setChecked(true);
                } else if (size.equals(getString(R.string.size_3xl))) {
                    binding.cbSize3XL.setChecked(true);
                } else if (size.equals(getString(R.string.size_4xl))) {
                    binding.cbSize4XL.setChecked(true);
                } else if (size.equals(getString(R.string.size_5xl))) {
                    binding.cbSize5XL.setChecked(true);
                } else if (size.equals(getString(R.string.size_6xl))) {
                    binding.cbSize6XL.setChecked(true);
                } else if (size.equals(getString(R.string.size_7xl))) {
                    binding.cbSize7XL.setChecked(true);
                } else if (size.equals(getString(R.string.size_8xl))) {
                    binding.cbSize8XL.setChecked(true);
                } else if (size.equals(getString(R.string.size_9xl))) {
                    binding.cbSize9XL.setChecked(true);
                }
            } else if (productSize.getSizeType() == ProductSize.TYPE_CUSTOM_SIZE) {
                binding.radioCustomSize.setChecked(true);
                if (i == 0) {
                    binding.layoutCustomSize1.inputBox.setText(productSize.getCustomSize());
                } else {
                    if (i >= customSizeBindings.size()) {
                        binding.btnAddMoreCustomSize.performClick();
                    }
                    customSizeBindings.get(i).inputBox.setText(productSize.getCustomSize());
                }
            }
        }
    }

    private boolean isFormValidated() {
        productSizes.clear();

        if (binding.radioOneSize.isChecked()) {
            addBrandSize(getString(R.string.one_size), true);

        } else if (binding.radioLetterSize.isChecked()) {
            if (binding.cbSizeXS.isChecked()) {
                addBrandSize(getString(R.string.size_xs), false);
            }
            if (binding.cbSizeS.isChecked()) {
                addBrandSize(getString(R.string.size_s), false);
            }
            if (binding.cbSizeM.isChecked()) {
                addBrandSize(getString(R.string.size_m), false);
            }
            if (binding.cbSizeL.isChecked()) {
                addBrandSize(getString(R.string.size_l), false);
            }
            if (binding.cbSizeXL.isChecked()) {
                addBrandSize(getString(R.string.size_xl), false);
            }
            if (binding.cbSize2XL.isChecked()) {
                addBrandSize(getString(R.string.size_2xl), false);
            }
            if (binding.cbSize3XL.isChecked()) {
                addBrandSize(getString(R.string.size_3xl), false);
            }
            if (binding.cbSize4XL.isChecked()) {
                addBrandSize(getString(R.string.size_4xl), false);
            }
            if (binding.cbSize5XL.isChecked()) {
                addBrandSize(getString(R.string.size_5xl), false);
            }
            if (binding.cbSize6XL.isChecked()) {
                addBrandSize(getString(R.string.size_6xl), false);
            }
            if (binding.cbSize7XL.isChecked()) {
                addBrandSize(getString(R.string.size_7xl), false);
            }
            if (binding.cbSize8XL.isChecked()) {
                addBrandSize(getString(R.string.size_8xl), false);
            }
            if (binding.cbSize9XL.isChecked()) {
                addBrandSize(getString(R.string.size_9xl), false);
            }
            if (productSizes.isEmpty()) {
                showMessage(getString(R.string.error_empty_letter_size));
                return false;
            }

        } else if (binding.radioCustomSize.isChecked()) {
            for (RowAddProductSizeBinding sizeBinding : customSizeBindings) {
                String size = sizeBinding.inputBox.getText().toString().trim();
                if (size.isEmpty()) {
                    showMessage(getString(R.string.error_empty_custom_size));
                    return false;
                }
                for (ProductSize productSize : productSizes) {
                    if (productSize.getCustomSize().equals(size)) {
                        showMessage(getString(R.string.error_duplicated_custom_size));
                        return false;
                    }
                }
                addCustomSize(size);
            }
            if (productSizes.isEmpty()) {
                showMessage(getString(R.string.error_empty_custom_size));
                return false;
            }
        }

        return true;
    }

    private void addBrandSize(String brandSize, boolean isOneSize) {
        ProductSize productSize = new ProductSize(productId,
                isOneSize ? ProductSize.TYPE_ONE_SIZE : ProductSize.TYPE_LETTER_SIZE);
        productSize.setBrandSize(brandSize);
        productSizes.add(productSize);
    }

    private void addCustomSize(String size) {
        ProductSize productSize = new ProductSize(productId, ProductSize.TYPE_CUSTOM_SIZE);
        productSize.setCustomSize(size);
        productSizes.add(productSize);
    }

    private void showMessage(String message) {
        Snackbar snackbar = Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
