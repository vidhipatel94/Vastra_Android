package com.esolution.vastrafashiondesigner.ui.newproduct.addcolor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.product.Color;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductColor;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivitySelectProductColorsBinding;
import com.esolution.vastrafashiondesigner.ui.newproduct.addsize.ProductSizesActivity;
import com.esolution.vastrafashiondesigner.ui.newproduct.addsize.SelectPrevAddedSizesDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SelectProductColorsActivity extends BaseActivity implements ProductColorAdapter.Listener {

    protected static final String EXTRA_PRODUCT = "extra_product";

    public static Intent createIntent(Context context, Product product) {
        Intent intent = new Intent(context, SelectProductColorsActivity.class);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    protected ActivitySelectProductColorsBinding binding;
    private ProgressDialogHandler progressDialogHandler;

    protected Product product;

    protected ArrayList<ProductColor> productColors = new ArrayList<>();
    private ArrayList<Color> colors = new ArrayList<>();

    protected ProductColorAdapter adapter;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivitySelectProductColorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogHandler = new ProgressDialogHandler(this);

        initView();

        getColors();
    }

    @Override
    protected View getRootView() {
        return binding.getRoot();
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            product = (Product) getIntent().getSerializableExtra(EXTRA_PRODUCT);
            return product != null;
        }
        return false;
    }

    private void initView() {
        setToolbarLayout();

        adapter = new ProductColorAdapter(productColors, this);
        binding.colorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.colorsRecyclerView.setAdapter(adapter);

        binding.btnAdd.setOnClickListener((v) -> {
            openSelectColorDialog(1);
        });

        binding.btnNext.setOnClickListener((v) -> {
            if (isFormValidated()) {
                openNextScreen();
            }
        });
    }

    private void setToolbarLayout() {
        binding.toolbarLayout.iconBack.setOnClickListener((view) -> onBackPressed());
        binding.toolbarLayout.title.setText(R.string.title_available_colors);
    }

    private void getColors() {
        progressDialogHandler.setProgress(true);
        subscriptions.add(RestUtils.getAPIs().getColors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        if (response.getData() != null) {
                            colors = response.getData();
                            if (productColors.isEmpty()) {
                                openSelectColorDialog(1);
                            }
                        } else {
                            showMessage(binding.getRoot(), getString(R.string.server_error));
                        }
                    } else {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(this, throwable);
                    showMessage(binding.getRoot(), message);
                }));
    }

    private ProductColor newProductColor;

    private void openSelectColorDialog(int colorLevel) {
        if (colors.isEmpty()) {
            showMessage(binding.getRoot(), getString(R.string.error_no_colors_found));
            return;
        }
        if (colorLevel == 1) {
            newProductColor = new ProductColor();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        SelectColorDialog dialog = SelectColorDialog.newInstance(colorLevel, colors,
                selectedColor -> {
                    if (selectedColor != null) {
                        if (colorLevel == 1 && isDuplicated(selectedColor, null)) {
                            showMessage(binding.getRoot(), getString(R.string.error_duplicate_color));
                            return;
                        }
                        boolean goNext = setColor(colorLevel, selectedColor, newProductColor);
                        if (goNext) {
                            openSelectColorDialog(colorLevel + 1);
                            return;
                        }
                    }
                    onProductColorSelected(newProductColor);
                });
        dialog.show(fragmentManager, SelectPrevAddedSizesDialog.class.getName());
    }

    private boolean setColor(int colorLevel, Color selectedColor, ProductColor productColor) {
        boolean goNext = colorLevel < 3;
        switch (colorLevel) {
            case 1: {
                productColor.setProminentColorId(selectedColor.getId());
                productColor.setProminentColorName(selectedColor.getName());
                productColor.setProminentColorHexCode(selectedColor.getHexCode());
                break;
            }
            case 2: {
                if (selectedColor.getId() == productColor.getProminentColorId()) {
                    goNext = false;
                    break;
                }
                productColor.setSecondaryColorId(selectedColor.getId());
                productColor.setSecondaryColorName(selectedColor.getName());
                productColor.setSecondaryColorHexCode(selectedColor.getHexCode());
                break;
            }
            case 3: {
                if (selectedColor.getId() == productColor.getProminentColorId() ||
                        selectedColor.getId() == productColor.getSecondaryColorId()) {
                    break;
                }
                productColor.setThirdColorId(selectedColor.getId());
                productColor.setThirdColorName(selectedColor.getName());
                productColor.setThirdColorHexCode(selectedColor.getHexCode());
                break;
            }
        }
        return goNext;
    }

    private boolean isDuplicated(@NotNull Color selectedColor, ProductColor updatingProductColor) {
        for (ProductColor productColor : productColors) {
            if (productColor.getProminentColorId() == selectedColor.getId() &&
                    (updatingProductColor == null ||
                            updatingProductColor.getProminentColorId() != productColor.getProminentColorId())) {
                return true;
            }
        }
        return false;
    }

    private void onProductColorSelected(@NotNull ProductColor productColor) {
        productColors.add(productColor);
        adapter.notifyItemInserted(productColors.size() - 1);
    }

    @Override
    public void onClickEditProductColor(int index) {
        ProductColor productColor = productColors.get(index);
        openUpdateColorDialog(1, productColor, index);
    }

    private void openUpdateColorDialog(int colorLevel, ProductColor productColor, int productIndex) {
        if (productColor == null) return;
        if (colors.isEmpty()) {
            showMessage(binding.getRoot(), getString(R.string.error_no_colors_found));
            return;
        }
        int index = -1;
        for (int i = 0; i < colors.size(); i++) {
            if (colorLevel == 1 && colors.get(i).getId() == productColor.getProminentColorId()) {
                index = i;
                break;
            } else if (colorLevel == 2 && colors.get(i).getId() == productColor.getSecondaryColorId()) {
                index = i;
                break;
            } else if (colorLevel == 3 && colors.get(i).getId() == productColor.getThirdColorId()) {
                index = i;
                break;
            }
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        SelectColorDialog dialog = SelectColorDialog.newInstance(colorLevel, colors,
                selectedColor -> {
                    if (selectedColor != null) {
                        if (colorLevel == 1 && isDuplicated(selectedColor, productColor)) {
                            showMessage(binding.getRoot(), getString(R.string.error_duplicate_color));
                            return;
                        }
                        boolean goNext = setColor(colorLevel, selectedColor, productColor);
                        if (goNext) {
                            openUpdateColorDialog(colorLevel + 1, productColor, productIndex);
                            return;
                        }
                    } else {
                        if (colorLevel == 2) {
                            productColor.setSecondaryColorId(0);
                            productColor.setSecondaryColorName(null);
                            productColor.setSecondaryColorHexCode(null);
                        }
                        if (colorLevel == 2 || colorLevel == 3) {
                            productColor.setThirdColorId(0);
                            productColor.setThirdColorName(null);
                            productColor.setThirdColorHexCode(null);
                        }
                    }
                    onProductColorUpdated(productColor, productIndex);
                }, index);
        dialog.show(fragmentManager, SelectPrevAddedSizesDialog.class.getName());
    }

    private void onProductColorUpdated(ProductColor productColor, int index) {
        productColors.set(index, productColor);
        adapter.notifyItemChanged(index);
    }

    @Override
    public void onClickDeleteProductColor(int index) {
        if (index >= productColors.size()) {
            return;
        }
        productColors.remove(index);
        adapter.notifyDataSetChanged();
    }

    private boolean isFormValidated() {
        if (productColors.isEmpty()) {
            showMessage(getRootView(), getString(R.string.error_empty_product_color));
            return false;
        }
        product.setColors(productColors);
        return true;
    }

    protected void openNextScreen() {
        startActivity(ProductSizesActivity.createIntent(this, product));
    }
}
