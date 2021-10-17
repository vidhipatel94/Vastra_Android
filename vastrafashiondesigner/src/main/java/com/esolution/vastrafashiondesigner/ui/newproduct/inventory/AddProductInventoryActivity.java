package com.esolution.vastrafashiondesigner.ui.newproduct.inventory;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductInventoryBinding;
import com.esolution.vastrafashiondesigner.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class AddProductInventoryActivity extends AppCompatActivity {

    private ActivityAddProductInventoryBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void setToolbarLayout() {
        binding.toolbarLayout.iconBack.setOnClickListener((view) -> onBackPressed());
        binding.toolbarLayout.title.setText(R.string.title_product_inventory);
    }
}
