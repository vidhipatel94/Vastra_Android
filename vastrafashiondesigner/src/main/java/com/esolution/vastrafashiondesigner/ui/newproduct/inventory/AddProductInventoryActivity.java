package com.esolution.vastrafashiondesigner.ui.newproduct.inventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductColor;
import com.esolution.vastrabasic.models.product.ProductInventory;
import com.esolution.vastrabasic.models.product.ProductSize;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductInventoryBinding;
import com.esolution.vastrafashiondesigner.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddProductInventoryActivity extends BaseActivity {

    protected static final String EXTRA_PRODUCT = "extra_product";

    public static Intent createIntent(Context context, Product product) {
        Intent intent = new Intent(context, AddProductInventoryActivity.class);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    protected ActivityAddProductInventoryBinding binding;
    private ProgressDialogHandler progressDialogHandler;

    protected Product product;

    protected List<ProductInventory> inventories = new ArrayList<>();
    protected ProductInventoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityAddProductInventoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogHandler = new ProgressDialogHandler(this);

        initView();
    }

    @Override
    protected View getRootView() {
        return binding.getRoot();
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            product = (Product) getIntent().getSerializableExtra(EXTRA_PRODUCT);
            return product != null && product.getColors() != null && product.getSizes() != null;
        }
        return false;
    }

    private void initView() {
        setToolbarLayout();

        setInventoryListView();

        binding.btnDone.setOnClickListener((v) -> {
            if (isFormValid()) {
                saveProductInventories();
            }
        });
    }

    private void setInventoryListView() {
        updateInventoriesList();

        adapter = new ProductInventoryAdapter(product.getColors(), inventories);
        binding.inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.inventoryRecyclerView.setAdapter(adapter);
    }

    protected void updateInventoriesList() {
        inventories.clear();

        for (ProductColor color : product.getColors()) {
            for (ProductSize size : product.getSizes()) {
                ProductInventory inventory = new ProductInventory(product.getId(), size.getId(), color.getId());
                inventory.setSizeName(size.getSizeText(this));
                inventories.add(inventory);
            }
        }
    }

    private void setToolbarLayout() {
        binding.toolbarLayout.iconBack.setOnClickListener((view) -> onBackPressed());
        binding.toolbarLayout.title.setText(R.string.title_product_inventory);
    }

    private boolean isFormValid() {
        if (adapter.hasAnyError()) {
            showMessage(binding.getRoot(), getString(R.string.error_invalid_inventory));
            return false;
        }
        inventories = adapter.getInventories();
        product.setInventories(inventories);
        return true;
    }

    protected void saveProductInventories() {
        progressDialogHandler.setProgress(true);
        DesignerLoginPreferences preferences = DesignerLoginPreferences.createInstance(this);
        subscriptions.add(RestUtils.getAPIs().addProductInventories(preferences.getSessionToken(), inventories)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        openNextScreen();
                    } else {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(this, throwable);
                    showMessage(binding.getRoot(), message);
                }));
    }

    protected void openNextScreen() {
        Intent intent = new Intent(AddProductInventoryActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
