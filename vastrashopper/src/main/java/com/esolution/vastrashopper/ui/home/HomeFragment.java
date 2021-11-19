package com.esolution.vastrashopper.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.ProductFilter;
import com.esolution.vastrabasic.models.product.BasicProduct;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.utils.JsonUtils;
import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.data.ShopperLoginPreferences;
import com.esolution.vastrashopper.databinding.FragmentHomeBinding;
import com.esolution.vastrashopper.ui.products.filters.ProductFilterActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.esolution.vastrabasic.utils.Utils.showMessage;

public class HomeFragment extends Fragment {

    protected CompositeDisposable subscriptions = new CompositeDisposable();

    //private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ProductFilter productFilter;
    private ArrayList<BasicProduct> basicProductsList = new ArrayList<>();

    public HomeAdapter homeAdapter;

    private ProgressDialogHandler progressDialogHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);*/

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initialization();

        getProductsFeed();

        /*final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        binding.btnSearch.setOnClickListener((v) -> {
            Intent intent = ProductFilterActivity.createIntent(getContext(), productFilter);
            onActivityResultLauncher.launch(intent);
        });
        return root;
    }

    ActivityResultLauncher<Intent> onActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.hasExtra(ProductFilterActivity.RESULT_FILTERED_PRODUCTS)) {
                            productFilter = (ProductFilter) data.
                                    getSerializableExtra(ProductFilterActivity.RESULT_FILTERED_PRODUCTS);
                            if (productFilter != null) {
                                getFilteredProductsList(productFilter);
                            }
                        }
                    }
                }
            }
    );

    private void initialization() {
        productFilter = new ProductFilter();
        if (getActivity() != null) {
            progressDialogHandler = new ProgressDialogHandler(getActivity());
        }
        binding.productList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        homeAdapter = new HomeAdapter(getContext(), basicProductsList);
        binding.productList.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();
    }

    private void getFilteredProductsList(ProductFilter productFilter) {
        ShopperLoginPreferences preferences = ShopperLoginPreferences.createInstance(getContext());

        progressDialogHandler.setProgress(true);
        subscriptions.add(RestUtils.getAPIs().
                getProducts(preferences.getSessionToken(), productFilter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        boolean success = true;
                        if (response.getData() == null) {
                            success = false;
                        } else {
                            basicProductsList.clear();
                            basicProductsList.addAll(response.getData());
                            homeAdapter.notifyDataSetChanged();
                            /*for (int i = 0; i < basicProductsList.size(); i++) {
                                Log.i("----", "Filtered Products: " +
                                        basicProductsList.get(i).getId() + " " +
                                        basicProductsList.get(i).getTitle());
                            }*/
                        }
                        if (!success) {
                            showMessage(binding.getRoot(), getString(R.string.server_error));
                        }
                    } else {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    progressDialogHandler.setProgress(false);
                    Log.i("------", "Failure: " + throwable);
                    String message = RestUtils.processThrowable(getContext(), throwable);
                    showMessage(binding.getRoot(), message);
                }));
    }

    private void getProductsFeed() {
        ShopperLoginPreferences preferences = ShopperLoginPreferences.createInstance(getContext());
        progressDialogHandler.setProgress(true);
        subscriptions.add(RestUtils.getAPIs().
                getProductFeeds(preferences.getSessionToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        boolean success = true;
                        if (response.getData() == null) {
                            success = false;
                        } else {
                            basicProductsList.clear();
                            basicProductsList.addAll(response.getData());
                            homeAdapter.notifyDataSetChanged();
                            /*for (int i = 0; i < basicProductsList.size(); i++) {
                                Log.i("----", "Fragment Products: " +
                                        basicProductsList.get(i).getId() + " " +
                                        basicProductsList.get(i).getTitle());
                            }*/
                        }
                        if (!success) {
                            showMessage(binding.getRoot(), getString(R.string.server_error));
                        }
                    } else {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    progressDialogHandler.setProgress(false);
                    //Log.i("------", "Failure: " + throwable);
                    String message = RestUtils.processThrowable(getContext(), throwable);
                    showMessage(binding.getRoot(), message);
                }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}