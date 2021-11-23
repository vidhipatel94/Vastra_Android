package com.esolution.vastrafashiondesigner.ui.products;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.BasicProduct;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrabasic.utils.AlertDialogHelper;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.databinding.ActivityCatalogueProductsBinding;
import com.esolution.vastrafashiondesigner.ui.newproduct.AddProductInfo1Activity;
import com.esolution.vastrafashiondesigner.ui.updateproduct.UpdateProductInfo1Activity;
import com.esolution.vastrafashiondesigner.ui.updateproduct.UpdateProductInventoryActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CatalogueProductsActivity extends BaseActivity {

    private static final String EXTRA_CATALOGUE = "extra_catalogue";

    public static final int RESULT_UPDATED = 101;

    public static Intent createIntent(Context context, Catalogue catalogue) {
        Intent intent = new Intent(context, CatalogueProductsActivity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        return intent;
    }

    private ActivityCatalogueProductsBinding binding;
    private Catalogue catalogue;

    private ProgressDialogHandler progressDialogHandler;
    private List<BasicProduct> products = new ArrayList<>();
    private ProductAdapter adapter;

    private boolean isListUpdated = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityCatalogueProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogHandler = new ProgressDialogHandler(this);

        initToolbar();

        initProductsListView();

        getProducts();

        binding.fab.setOnClickListener((v) -> {
            onClickAddNewProduct();
        });
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            catalogue = (Catalogue) getIntent().getSerializableExtra(EXTRA_CATALOGUE);
            return catalogue != null;
        }
        return false;
    }

    private SearchView searchView;

    private void initToolbar() {
        binding.toolbarLayout.title.setText(catalogue.getName());
        binding.toolbarLayout.iconBack.setOnClickListener(v -> onBackPressed());

        binding.toolbarLayout.layoutMenu.setVisibility(View.VISIBLE);

        searchView = new SearchView(this);
        searchView.setQueryHint(getString(R.string.hint_search_product));
        searchView.setOnSearchClickListener(v -> {
            binding.toolbarLayout.title.setVisibility(View.GONE);
        });
        searchView.setOnCloseListener(() -> {
            binding.toolbarLayout.title.setVisibility(View.VISIBLE);
            return false;
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        binding.toolbarLayout.layoutMenu.addView(searchView);
    }

    private void getProducts() {
        progressDialogHandler.setProgress(true);
        DesignerLoginPreferences preferences = DesignerLoginPreferences.createInstance(this);
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
        adapter = new ProductAdapter(products, new ProductAdapter.ProductListListener() {
            @Override
            public void onClickMenu(View view, int position) {
                openPopUpMenu(view, position);
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
        });
        binding.productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.productsRecyclerView.setAdapter(adapter);
    }

    private void openPopUpMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.popup_menu_product);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_edit_info) {
                    onClickEditProductInfo(position);
                } else if (item.getItemId() == R.id.action_edit_inventory) {
                    onClickEditProductInventory(position);
                } else if (item.getItemId() == R.id.action_delete) {
                    onClickDeleteProduct(position);
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void onClickEditProductInfo(int position) {
        getProductInfo(products.get(position), true, false);
    }

    private void onClickEditProductInventory(int position) {
        getProductInfo(products.get(position), false, true);
    }

    private void getProductInfo(BasicProduct product, boolean editInfo, boolean editInventory) {
        progressDialogHandler.setProgress(true);
        DesignerLoginPreferences preferences = DesignerLoginPreferences.createInstance(this);
        subscriptions.add(RestUtils.getAPIs().getProductInfo(preferences.getSessionToken(), product.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        if (editInfo) {
                            openEditProductInfo(response.getData());
                        } else if (editInventory) {
                            openEditInventory(response.getData());
                        }
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

    private void openEditProductInfo(Product product) {
        Intent intent = UpdateProductInfo1Activity.createIntent(this, catalogue, product);
        startActivity(intent);
    }

    private void openEditInventory(Product product) {
        Intent intent = UpdateProductInventoryActivity.createIntent(this, product);
        startActivity(intent);
    }

    private void onClickDeleteProduct(int position) {
        BasicProduct product = products.get(position);
        AlertDialogHelper.showDialog(this, getString(R.string.title_delete_product),
                product.getTitle() + "\n\n" + getString(R.string.msg_delete_product),
                getString(R.string.delete), getString(R.string.cancel), new AlertDialogHelper.Listener() {
                    @Override
                    public void onClickPositiveButton() {
                        deleteProduct(product, position);
                    }

                    @Override
                    public void onClickNegativeButton() {

                    }
                });
    }

    private void deleteProduct(BasicProduct product, int position) {
        progressDialogHandler.setProgress(true);
        DesignerLoginPreferences preferences = DesignerLoginPreferences.createInstance(this);
        subscriptions.add(RestUtils.getAPIs().deleteProduct(preferences.getSessionToken(), product.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        isListUpdated = true;
                        products.remove(position);
                        adapter.notifyItemRemoved(position);
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

    private void onClickAddNewProduct() {
        startActivity(AddProductInfo1Activity.createIntent(this, catalogue));
    }

    @Override
    public void onBackPressed() {
        if (searchView != null && !searchView.isIconified()) {
            searchView.setIconified(true);
        } else {
            finish();
        }
    }

    @Override
    public void finish() {
        if (isListUpdated) {
            setResult(RESULT_UPDATED);
        }
        super.finish();
    }
}
