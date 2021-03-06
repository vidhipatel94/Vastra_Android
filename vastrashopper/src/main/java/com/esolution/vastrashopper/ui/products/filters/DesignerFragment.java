package com.esolution.vastrashopper.ui.products.filters;

import static com.esolution.vastrabasic.utils.Utils.showMessage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.data.ShopperLoginPreferences;
import com.esolution.vastrashopper.databinding.FragmentFilterBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DesignerFragment extends FilterFragment {

    protected CompositeDisposable subscriptions = new CompositeDisposable();

    private FragmentFilterBinding binding;
    private DesignerAdapter designerAdapter;

    private ProgressDialogHandler progressDialogHandler;
    private final ArrayList<Designer> designers = new ArrayList<>();

    private final List<Integer> prevSelectedDesigners;

    public DesignerFragment(List<Integer> prevSelectedDesigners) {
        this.prevSelectedDesigners = prevSelectedDesigners;
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

        getDesigners();

        return root;
    }

    private void initView() {
        binding.filterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        designerAdapter = new DesignerAdapter(designers, prevSelectedDesigners);
        binding.filterRecyclerView.setAdapter(designerAdapter);
        designerAdapter.notifyDataSetChanged();
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
                        if (response.getData() != null) {
                            designers.clear();
                            designers.addAll(response.getData());
                            designerAdapter.notifyDataSetChanged();
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
        if (designerAdapter == null) return null;
        return designerAdapter.getSelectedDesigners();
    }
}

