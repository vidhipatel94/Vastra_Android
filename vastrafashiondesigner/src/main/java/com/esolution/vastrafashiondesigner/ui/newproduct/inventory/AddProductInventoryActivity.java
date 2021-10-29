package com.esolution.vastrafashiondesigner.ui.newproduct.inventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductInventoryBinding;
import com.esolution.vastrafashiondesigner.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class AddProductInventoryActivity extends BaseActivity {

    private static final String EXTRA_CATALOGUE = "extra_catalogue";
    private static final String EXTRA_PRODUCT = "extra_product";

    public static Intent createIntent(Context context, Catalogue catalogue, Product product) {
        Intent intent = new Intent(context, AddProductInventoryActivity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    private ActivityAddProductInventoryBinding binding;

    private Catalogue catalogue;
    private Product product;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityAddProductInventoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setToolbarLayout();

        List<String> sizes = new ArrayList<>();
        sizes.add("S");
        sizes.add("M");
        sizes.add("L");

        ProductInventoryAdapter adapter = new ProductInventoryAdapter(sizes);
        binding.inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.inventoryRecyclerView.setAdapter(adapter);

        binding.btnDone.setOnClickListener((v) -> {
            Intent intent = new Intent(AddProductInventoryActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        binding.toolbarLayout.title.setText(R.string.title_product_inventory);
    }
}
