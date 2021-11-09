package com.esolution.vastrafashiondesigner.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.BasicCatalogue;
import com.esolution.vastrabasic.models.product.BasicProduct;
import com.esolution.vastrabasic.ui.BaseFragment;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.databinding.FragmentCataloguesBinding;
import com.esolution.vastrafashiondesigner.databinding.RowCatalogueBinding;
import com.esolution.vastrafashiondesigner.ui.startup.RegisterCreateCatalogueActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CataloguesFragment extends BaseFragment {

    private FragmentCataloguesBinding binding;
    private ProgressDialogHandler progressDialogHandler;

    private List<BasicCatalogue> catalogues = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialogHandler = new ProgressDialogHandler(getActivity());

        getCatalogues();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCataloguesBinding.inflate(inflater, container, false);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        binding.btnAdd.setOnClickListener((v) -> {
            Intent intent = new Intent(getContext(), RegisterCreateCatalogueActivity.class);
            startActivity(intent);
        });
    }

    private void getCatalogues() {
        progressDialogHandler.setProgress(true);
        DesignerLoginPreferences preferences = DesignerLoginPreferences.createInstance(getContext());
        int designerId = preferences.getDesignerId();
        subscriptions.add(RestUtils.getAPIs().getCatalogues(preferences.getSessionToken(), designerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        catalogues.clear();
                        List<BasicCatalogue> data = response.getData();
                        if (data != null) {
                            catalogues.addAll(data);
                        }
                        refreshView();
                    } else {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(getContext(), throwable);
                    showMessage(binding.getRoot(), message);
                }));
    }

    private void refreshView() {
        binding.cataloguesLayout.removeAllViews();

        int totalCatalogues = catalogues.size();
        if (totalCatalogues > 0) {
            binding.emptyListTextView.setVisibility(View.INVISIBLE);
            for (int i = 0; i < totalCatalogues; i++) {
                BasicCatalogue catalogue = catalogues.get(i);

                RowCatalogueBinding catalogueBinding = RowCatalogueBinding.inflate(getLayoutInflater());
                binding.cataloguesLayout.addView(catalogueBinding.getRoot());

                catalogueBinding.title.setText(catalogue.getName());

                boolean isProductsEmpty = true;
                if (catalogue.getProducts() != null) {
                    List<BasicProduct> products = catalogue.getProducts();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    catalogueBinding.products.setLayoutManager(layoutManager);
                    catalogueBinding.products.setAdapter(new CatalogueProductsAdapter(products, product -> {
                        onClickProduct(product.getId());
                    }));

                    isProductsEmpty = products.size() == 0;
                }

                catalogueBinding.emptyListTextView.setVisibility(isProductsEmpty ? View.VISIBLE : View.GONE);
                catalogueBinding.linkShowAll.setVisibility(isProductsEmpty ? View.GONE : View.VISIBLE);

                catalogueBinding.linkShowAll.setOnClickListener((v) -> {
                    Intent intent = new Intent(getContext(), CatalogueProductsActivity.class);
                    startActivity(intent);
                });
            }
        } else {
            binding.emptyListTextView.setVisibility(View.VISIBLE);
        }
    }

    private void onClickProduct(int id) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}