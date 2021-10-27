package com.esolution.vastrafashiondesigner.ui.newproduct.addcolor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrabasic.utils.JsonUtils;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivitySelectProductColorsBinding;
import com.esolution.vastrafashiondesigner.ui.newproduct.addsize.ProductSizesActivity;
import com.esolution.vastrafashiondesigner.ui.newproduct.addsize.SelectPrevAddedSizesDialog;

public class SelectProductColorsActivity extends BaseActivity {

    private static final String EXTRA_CATALOGUE = "extra_catalogue";
    private static final String EXTRA_PRODUCT = "extra_product";

    public static Intent createIntent(Context context, Catalogue catalogue, Product product) {
        Intent intent = new Intent(context, SelectProductColorsActivity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    private ActivitySelectProductColorsBinding binding;

    private Catalogue catalogue;
    private Product product;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivitySelectProductColorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setToolbarLayout();

        ProductColorAdapter adapter = new ProductColorAdapter();
        binding.colorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.colorsRecyclerView.setAdapter(adapter);

        binding.btnAdd.setOnClickListener((v) -> {
            openSelectColorDialog(1);
        });

        binding.btnNext.setOnClickListener((v) -> {
            Intent intent = new Intent(SelectProductColorsActivity.this, ProductSizesActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected View getRootView() {
        return binding.getRoot();
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            catalogue = (Catalogue) getIntent().getSerializableExtra(EXTRA_CATALOGUE);
            product = (Product) getIntent().getSerializableExtra(EXTRA_PRODUCT);
            return catalogue != null && product != null;
        }
        return false;
    }

    private void setToolbarLayout() {
        binding.toolbarLayout.iconBack.setOnClickListener((view) -> onBackPressed());
        binding.toolbarLayout.title.setText(R.string.title_available_colors);
    }

    private void openSelectColorDialog(int colorLevel) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SelectColorDialog dialog = SelectColorDialog.newInstance(colorLevel,
                new SelectColorDialog.SelectColorListener() {
                    @Override
                    public void onColorSelected() {
                        if (colorLevel < 3) {
                            openSelectColorDialog(colorLevel + 1);
                        }
                    }
                });
        dialog.show(fragmentManager, SelectPrevAddedSizesDialog.class.getName());
    }
}
