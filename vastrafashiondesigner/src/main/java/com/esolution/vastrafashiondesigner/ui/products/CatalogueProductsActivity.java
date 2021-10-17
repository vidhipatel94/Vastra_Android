package com.esolution.vastrafashiondesigner.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivityCatalogueProductsBinding;
import com.esolution.vastrafashiondesigner.databinding.LayoutToolbarMenuItemBinding;
import com.esolution.vastrafashiondesigner.ui.newproduct.AddProductInfo1Activity;

public class CatalogueProductsActivity extends AppCompatActivity {

    private ActivityCatalogueProductsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatalogueProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();

        ProductAdapter adapter = new ProductAdapter();
        binding.productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.productsRecyclerView.setAdapter(adapter);

        binding.btnAdd.setOnClickListener((v) -> {
            Intent intent = new Intent(CatalogueProductsActivity.this, AddProductInfo1Activity.class);
            startActivity(intent);
        });
    }

    private void initToolbar() {
        binding.toolbarLayout.title.setText(R.string.catalogue_name);
        binding.toolbarLayout.iconBack.setOnClickListener(v -> onBackPressed());

        binding.toolbarLayout.layoutMenu.setVisibility(View.VISIBLE);
        LayoutToolbarMenuItemBinding menuItemBinding = LayoutToolbarMenuItemBinding.inflate(getLayoutInflater());
        binding.toolbarLayout.layoutMenu.addView(menuItemBinding.getRoot());

        menuItemBinding.actionMenuItem.setImageDrawable(ContextCompat.getDrawable(this,
                R.drawable.ic_search_grey_500_24dp));
    }
}
