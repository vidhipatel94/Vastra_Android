package com.esolution.vastrashopper.ui.designers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.BasicProduct;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrabasic.utils.JsonUtils;
import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.data.ShopperLoginPreferences;
import com.esolution.vastrashopper.databinding.ActivityDesignerCatalogueProductsBinding;
import com.esolution.vastrashopper.ui.products.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CatalogueProductsActivity extends BaseActivity implements ProductAdapter.ProductListListener {

    private static final String EXTRA_CATALOGUE = "extra_catalogue";

    public static Intent createIntent(Context context, Catalogue catalogue) {
        Intent intent = new Intent(context, CatalogueProductsActivity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        return intent;
    }

    private ActivityDesignerCatalogueProductsBinding binding;
    private Catalogue catalogue;

    private ProgressDialogHandler progressDialogHandler;
    private List<BasicProduct> products = new ArrayList<>();
    private ProductAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityDesignerCatalogueProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogHandler = new ProgressDialogHandler(this);

        initToolbar();

        initProductsListView();

        getProducts();
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            catalogue = (Catalogue) getIntent().getSerializableExtra(EXTRA_CATALOGUE);
            Log.d("-----", "getIntentData: " + JsonUtils.toJson(catalogue));
            return catalogue != null;
        }
        return false;
    }

    private void initToolbar() {
        binding.toolbarLayout.title.setText(catalogue.getName());
        binding.toolbarLayout.iconBack.setOnClickListener(v -> onBackPressed());

        binding.toolbarLayout.layoutMenu.setVisibility(View.VISIBLE);
    }

    private void getProducts() {
        progressDialogHandler.setProgress(true);
        ShopperLoginPreferences preferences = ShopperLoginPreferences.createInstance(this);
        subscriptions.add(RestUtils.getAPIs().getCatalogueProducts(preferences.getSessionToken(), catalogue.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        products.clear();
                        if (response.getData() != null) {
                            products.addAll(response.getData());
                        }
                        adapter.notifyDataSetChanged();
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

    private void initProductsListView() {
        adapter = new ProductAdapter(products, this);
        binding.productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.productsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickLike(int position) {
        BasicProduct product = products.get(position);
        product.setUserLiked(!product.isUserLiked());
        product.setTotalLikes(product.isUserLiked() ?
                product.getTotalLikes() + 1 : product.getTotalLikes() - 1);
        adapter.notifyItemChanged(position);
        showMessage(binding.getRoot(), getString(R.string.not_implemented));
    }

    @Override
    public void onClickProduct(int position) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("Product", products.get(position));
        startActivity(intent);
    }
}
