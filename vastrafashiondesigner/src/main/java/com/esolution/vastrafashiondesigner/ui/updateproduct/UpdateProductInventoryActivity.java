package com.esolution.vastrafashiondesigner.ui.updateproduct;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductColor;
import com.esolution.vastrabasic.models.product.ProductInventory;
import com.esolution.vastrabasic.models.product.ProductSize;
import com.esolution.vastrabasic.utils.JsonUtils;
import com.esolution.vastrafashiondesigner.ui.newproduct.inventory.AddProductInventoryActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UpdateProductInventoryActivity extends AddProductInventoryActivity {

    public static Intent createIntent(Context context, Product product) {
        Intent intent = new Intent(context, UpdateProductInventoryActivity.class);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.btnEditColors.setVisibility(View.VISIBLE);
        binding.btnEditSizes.setVisibility(View.VISIBLE);

        binding.btnEditColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UpdateProductColorsActivity.createIntent(UpdateProductInventoryActivity.this, product);
                editColorsResultLauncher.launch(intent);
            }
        });

        binding.btnEditSizes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UpdateProductSizesActivity.createIntent(UpdateProductInventoryActivity.this, product);
                editSizesResultLauncher.launch(intent);
            }
        });
    }

    private ActivityResultLauncher<Intent> editColorsResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.hasExtra(UpdateProductColorsActivity.RESULT_PRODUCT)) {
                            Product p = (Product) data.getSerializableExtra(UpdateProductColorsActivity.RESULT_PRODUCT);
                            if (p != null && p.getColors() != null) {
                                onEditColors(p.getColors());
                            }
                        }
                    }
                }
            });

    private void onEditColors(@NotNull List<ProductColor> colors) {
        product.setColors(colors);
        updateInventoriesList();
        adapter.setColorsAndInventories(product.getColors(), inventories);
    }

    private ActivityResultLauncher<Intent> editSizesResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.hasExtra(UpdateProductSizesActivity.RESULT_PRODUCT)) {
                            Product p = (Product) data.getSerializableExtra(UpdateProductSizesActivity.RESULT_PRODUCT);
                            if (p != null && p.getSizes() != null) {
                                onEditSizes(p.getSizes());
                            }
                        }
                    }
                }
            });

    private void onEditSizes(@NotNull List<ProductSize> sizes) {
        Log.d("------", "onEditSizes: "+JsonUtils.toJson(sizes));
        product.setSizes(sizes);
        updateInventoriesList();
        adapter.setColorsAndInventories(product.getColors(), inventories);
    }

    private boolean isInventoriesSet = false;

    @Override
    protected void updateInventoriesList() {
        List<ProductInventory> prevList = new ArrayList<>(inventories);
        if (!isInventoriesSet && product.getInventories() != null) {
            prevList = product.getInventories();
            isInventoriesSet = true;
        }

        super.updateInventoriesList();

        // restore qty values
        for (int i = 0; i < inventories.size(); i++) {
            ProductInventory inventory = inventories.get(i);
            for (ProductInventory prevInventory : prevList) {
                if (inventory.getProductSizeId() == prevInventory.getProductSizeId() &&
                        inventory.getProductColorId() == prevInventory.getProductColorId()) {
                    inventory.setQuantityAvailable(prevInventory.getQuantityAvailable());
                    inventory.setId(prevInventory.getId());
                    inventories.set(i, inventory);
                    break;
                }
            }
        }
    }

    @Override
    protected void saveProductInventories() {
        Log.d("-------", "saveProductInventories: " + JsonUtils.toJson(inventories));
    }

}
