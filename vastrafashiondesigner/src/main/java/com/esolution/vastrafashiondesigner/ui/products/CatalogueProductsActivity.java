package com.esolution.vastrafashiondesigner.ui.products;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivityCatalogueProductsBinding;

public class CatalogueProductsActivity extends AppCompatActivity {

    private ActivityCatalogueProductsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatalogueProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarLayout.title.setText(R.string.catalogue_name);
        binding.toolbarLayout.iconBack.setOnClickListener(v -> onBackPressed());

        ProductAdapter adapter = new ProductAdapter();
        binding.productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.productsRecyclerView.setAdapter(adapter);
    }
}
