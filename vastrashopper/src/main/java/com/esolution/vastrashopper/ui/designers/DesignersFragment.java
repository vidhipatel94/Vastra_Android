package com.esolution.vastrashopper.ui.designers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.data.ShopperLoginPreferences;
import com.esolution.vastrashopper.databinding.FragmentDesignersBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.esolution.vastrabasic.utils.Utils.showMessage;

public class DesignersFragment extends Fragment {

    private final CompositeDisposable subscriptions = new CompositeDisposable();

    //private DesignersViewModel designersViewModel;
    private FragmentDesignersBinding binding;
    private DesignersAdapter designersAdapter;
    private RecyclerView designerRecyclerView;
    private final List<Designer> designersList = new ArrayList<>();

    private ProgressDialogHandler progressDialogHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*designersViewModel =
                new ViewModelProvider(this).get(DesignersViewModel.class);*/

        binding = FragmentDesignersBinding.inflate(inflater, container, false);

        initView();
        bindRecyclerViewAdapter();

        if (getActivity() != null) {
            progressDialogHandler = new ProgressDialogHandler(getActivity());
        }

        getDesigners();

        return binding.getRoot();
    }

    private void initView() {
        designerRecyclerView = binding.designerRecyclerView;
        designersAdapter = new DesignersAdapter(designersList);
    }

    private void bindRecyclerViewAdapter() {
        designerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        designerRecyclerView.setAdapter(designersAdapter);
        designersAdapter.notifyDataSetChanged();
    }

    private void getDesigners() {
        progressDialogHandler.setProgress(true);
        subscriptions.add(RestUtils.getAPIs()
                .getDesigners(ShopperLoginPreferences.createInstance(requireContext()).getSessionToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        boolean success = true;
                        if (response.getData() == null) {
                            success = false;
                        } else {
                            designersList.clear();
                            designersList.addAll(response.getData());
                            designersAdapter.notifyDataSetChanged();
                        }
                        if (!success) {
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}