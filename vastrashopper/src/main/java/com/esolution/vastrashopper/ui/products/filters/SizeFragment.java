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
import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.databinding.FragmentFilterBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.esolution.vastrabasic.utils.Utils.showMessage;

public class SizeFragment extends Fragment {

    protected CompositeDisposable subscriptions = new CompositeDisposable();

    private FragmentFilterBinding binding;
    private SizeAdapter sizeAdapter;

    private ProgressDialogHandler progressDialogHandler;
    private ArrayList<String> customSizes = new ArrayList<>();
    private String[] letterSizes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if(getActivity() != null) {
            progressDialogHandler = new ProgressDialogHandler(getActivity());
        }

        initView();

        getCustomProductSizes();

        return root;
    }

    private void initView() {
        binding.filterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        letterSizes = getResources().getStringArray(R.array.letter_sizes);
        sizeAdapter = new SizeAdapter(customSizes,letterSizes);
        binding.filterRecyclerView.setAdapter(sizeAdapter);
        sizeAdapter.notifyDataSetChanged();
    }

    private void getCustomProductSizes() {
        progressDialogHandler.setProgress(true);
        subscriptions.add(RestUtils.getAPIs().getCustomProductSizes()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {
            progressDialogHandler.setProgress(false);
            if(response.isSuccess()) {
                if(response.getData() != null) {
                    customSizes.clear();
                    customSizes.addAll(response.getData());
                    Collections.sort(customSizes);
                    sizeAdapter.notifyDataSetChanged();
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

