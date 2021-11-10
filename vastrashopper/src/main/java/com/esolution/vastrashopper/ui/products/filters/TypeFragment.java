package com.esolution.vastrashopper.ui.products.filters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.product.ProductType;
import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.databinding.FragmentFilterBinding;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.esolution.vastrabasic.utils.Utils.showMessage;

public class TypeFragment extends Fragment {

    protected CompositeDisposable subscriptions = new CompositeDisposable();

    private FragmentFilterBinding binding;
    private TypeAdapter typeAdapter;

    private ProgressDialogHandler progressDialogHandler;
    private ArrayList<ProductType> allProductTypes = new ArrayList<>();
    private ArrayList<ProductType> displayingProductTypes = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getActivity() != null) {
            progressDialogHandler = new ProgressDialogHandler(getActivity());
        }

        initView();

        getAllProductTypes();

        return root;
    }

    private void initView() {
        binding.filterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        typeAdapter = new TypeAdapter(displayingProductTypes, new TypeAdapter.Listener() {
            @Override
            public void onGenderAgeChanged(int gender, int ageGroup) {
                displayingProductTypes.clear();
                for(ProductType productType : allProductTypes) {
                    if(productType.getGender() == gender && productType.getAgeGroup() == ageGroup) {
                        displayingProductTypes.add(productType);
                    }
                }
                typeAdapter.notifyDataSetChanged();
            }
        });
        binding.filterRecyclerView.setAdapter(typeAdapter);
        typeAdapter.notifyDataSetChanged();
    }

    private void getAllProductTypes() {
        progressDialogHandler.setProgress(true);
        subscriptions.add(RestUtils.getAPIs().getProductTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        if (response.getData() != null) {
                            allProductTypes.clear();
                            allProductTypes.addAll(response.getData());
                        } else {
                            showMessage(binding.getRoot(), getString(R.string.server_error));
                        }
                    } else {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(getContext(), throwable);
                    showMessage(binding.getRoot(), message);
                }));
    }
}
