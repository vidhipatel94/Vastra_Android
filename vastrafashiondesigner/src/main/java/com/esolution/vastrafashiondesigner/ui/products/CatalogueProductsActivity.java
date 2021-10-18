package com.esolution.vastrafashiondesigner.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivityCatalogueProductsBinding;
import com.esolution.vastrafashiondesigner.ui.newproduct.AddProductInfo1Activity;

public class CatalogueProductsActivity extends AppCompatActivity {

    private ActivityCatalogueProductsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatalogueProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();

        initProductsListView();

        binding.fab.setOnClickListener((v) -> {
            Intent intent = new Intent(CatalogueProductsActivity.this, AddProductInfo1Activity.class);
            startActivity(intent);
        });
    }

    private SearchView searchView;

    private void initToolbar() {
        binding.toolbarLayout.title.setText("Catalogue name");
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

    private void initProductsListView() {
        ProductAdapter adapter = new ProductAdapter(new ProductAdapter.ProductListListener() {
            @Override
            public void onClickMenu(View view, int position) {
                openPopUpMenu(view, position);
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
                if (item.getItemId() == R.id.action_edit) {
                    editProduct(position);
                } else if (item.getItemId() == R.id.action_delete) {
                    deleteProduct(position);
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void editProduct(int position) {

    }

    private void deleteProduct(int position) {

    }

    @Override
    public void onBackPressed() {
        if (searchView != null && !searchView.isIconified()) {
            searchView.setIconified(true);
        } else {
            super.onBackPressed();
        }
    }
}
