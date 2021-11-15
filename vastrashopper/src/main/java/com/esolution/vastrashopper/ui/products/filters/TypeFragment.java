package com.esolution.vastrashopper.ui.products.filters;

import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.esolution.vastrabasic.utils.Utils.showMessage;

public class TypeFragment extends FilterFragment {

    protected CompositeDisposable subscriptions = new CompositeDisposable();

    private FragmentFilterBinding binding;
    private TypeAdapter typeAdapter;

    private ProgressDialogHandler progressDialogHandler;
    private final ArrayList<ProductType> allProductTypes = new ArrayList<>();
    private final ArrayList<ProductType> displayingProductTypes = new ArrayList<>();
    private final List<Integer> prevSelectedTypes;

    private int prevSelectedAgeGroup;
    private int prevSelectedGender;

    public TypeFragment(List<Integer> prevSelectedTypes, int prevSelectedAgeGroup, int prevSelectedGender) {
        this.prevSelectedTypes = prevSelectedTypes;
        this.prevSelectedAgeGroup = prevSelectedAgeGroup;
        this.prevSelectedGender = prevSelectedGender;
    }

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
        binding.filterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));
        Log.i("Gender", "PrevSelectedGender: " + prevSelectedGender);
        Log.i("AgeGroup", "PrevSelectedAgeGroup: " + prevSelectedAgeGroup);
        typeAdapter = new TypeAdapter(displayingProductTypes, prevSelectedTypes,
                prevSelectedAgeGroup, prevSelectedGender,
                new TypeAdapter.Listener() {
                    @Override
                    public void onGenderAgeChanged(int gender, int ageGroup) {
                        prevSelectedAgeGroup = ageGroup;
                        prevSelectedGender = gender;
                        updateDisplayingProductTypes();
                    }
                });
        binding.filterRecyclerView.setAdapter(typeAdapter);
        typeAdapter.notifyDataSetChanged();
    }

    private void updateDisplayingProductTypes() {
        Log.i("----", "Gender: " + prevSelectedGender + " Age " + prevSelectedAgeGroup + " SIZE" + allProductTypes.size());
        displayingProductTypes.clear();
        for (ProductType productType : allProductTypes) {
            if (productType.getGender() == prevSelectedGender && productType.getAgeGroup() == prevSelectedAgeGroup) {
                displayingProductTypes.add(productType);
            }
        }
        Log.d("...", "onGenderAgeChanged: " + displayingProductTypes.size());
        //typeAdapter.notifyDataSetChanged();
        binding.filterRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                typeAdapter.notifyDataSetChanged();
            }
        });
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
                            updateDisplayingProductTypes();
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

    @Override
    protected ArrayList<Integer> getSelectedData() {
        if (typeAdapter == null) return null;
        return typeAdapter.getSelectedProductTypes();
    }

    @Override
    protected int getSelectedGender() {
        if (typeAdapter == null) return -1;
        return typeAdapter.getPrevSelectedGender();
    }

    @Override
    protected int getSelectedAgeGroup() {
        if (typeAdapter == null) return -1;
        return typeAdapter.getPrevSelectedAgeGroup();
    }
}
