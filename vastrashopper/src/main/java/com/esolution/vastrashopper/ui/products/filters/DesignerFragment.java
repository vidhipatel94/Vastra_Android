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
import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.databinding.FragmentFilterBinding;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.esolution.vastrabasic.utils.Utils.showMessage;

public class DesignerFragment extends Fragment {

    protected CompositeDisposable subscriptions = new CompositeDisposable();

    private FragmentFilterBinding binding;
    private DesignerAdapter designerAdapter;

    private ProgressDialogHandler progressDialogHandler;
    private ArrayList<Designer> designers = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if(getActivity() != null) {
            progressDialogHandler = new ProgressDialogHandler(getActivity());
        }

        initView();

        getDesigners();

        return root;
    }

    private void initView() {
        binding.filterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        designerAdapter = new DesignerAdapter(designers);
        binding.filterRecyclerView.setAdapter(designerAdapter);
        designerAdapter.notifyDataSetChanged();
    }

    private void getDesigners() {
        progressDialogHandler.setProgress(true);
        subscriptions.add(RestUtils.getAPIs().getDesigners()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {
            progressDialogHandler.setProgress(false);
            if(response.isSuccess()) {
                if(response.getData() != null){
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
}

