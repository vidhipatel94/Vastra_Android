package com.esolution.vastrafashiondesigner.ui.products;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.BasicCatalogue;
import com.esolution.vastrabasic.models.product.BasicProduct;
import com.esolution.vastrabasic.ui.BaseFragment;
import com.esolution.vastrabasic.ui.products.CatalogueProductsAdapter;
import com.esolution.vastrafashiondesigner.FashionDesignerHandler;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.databinding.FragmentCataloguesBinding;
import com.esolution.vastrabasic.databinding.RowCatalogueBinding;
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
        refreshView();

        return binding.getRoot();
    }

    private void initView() {
        binding.btnAdd.setOnClickListener((v) -> {
            startActivity(RegisterCreateCatalogueActivity.createIntent(getContext(), true));
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
                    } else if (binding != null) {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(getContext(), throwable);
                    if (binding != null) {
                        showMessage(binding.getRoot(), message);
                    }
                }));
    }

    private void refreshView() {
        if (binding == null) return;

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
                        onClickProduct(product);
                    }));

                    isProductsEmpty = products.size() == 0;
                }

                catalogueBinding.emptyListTextView.setVisibility(isProductsEmpty ? View.VISIBLE : View.GONE);
                catalogueBinding.linkShowAll.setVisibility(isProductsEmpty ? View.GONE : View.VISIBLE);

                catalogueBinding.linkShowAll.setOnClickListener((v) -> {
                    openCatalogueProducts(catalogue);
                });
            }
        } else {
            binding.emptyListTextView.setVisibility(View.VISIBLE);
        }
    }

    private void openCatalogueProducts(BasicCatalogue catalogue) {
        Intent intent = CatalogueProductsActivity.createIntent(requireContext(), catalogue);
        activityResultLauncher.launch(intent);
    }

    private void onClickProduct(BasicProduct basicProduct) {
        if (FashionDesignerHandler.getListener() != null) {
            FashionDesignerHandler.getListener().openProductDetail(requireContext(), basicProduct);
        }
    }

    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        CataloguesFragment.this.onActivityResult(result);
                    }
                });
    }

    private void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == CatalogueProductsActivity.RESULT_UPDATED) {
            getCatalogues();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}