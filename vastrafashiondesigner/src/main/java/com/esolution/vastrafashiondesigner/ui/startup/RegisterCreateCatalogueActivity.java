package com.esolution.vastrafashiondesigner.ui.startup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.BasicCatalogue;
import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.databinding.ActivityRegisterCreateCatalogueBinding;
import com.esolution.vastrafashiondesigner.ui.newproduct.AddProductInfo1Activity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterCreateCatalogueActivity extends BaseActivity {

    private static final String EXTRA_IS_CREATE_OR_SELECT_CATALOGUE = "extra_is_create_or_select_catalogue";

    public static Intent createIntent(Context context, boolean createOrSelectCatalogue) {
        Intent intent = new Intent(context, RegisterCreateCatalogueActivity.class);
        intent.putExtra(EXTRA_IS_CREATE_OR_SELECT_CATALOGUE, createOrSelectCatalogue);
        return intent;
    }

    private ActivityRegisterCreateCatalogueBinding binding;
    private ProgressDialogHandler progressDialogHandler;

    private List<Catalogue> catalogues = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterCreateCatalogueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogHandler = new ProgressDialogHandler(this);

        if (getIntent().getBooleanExtra(EXTRA_IS_CREATE_OR_SELECT_CATALOGUE, false)) {
            binding.btnBack.setVisibility(View.VISIBLE);
            binding.btnBack.setOnClickListener(v -> onBackPressed());

            binding.title.setText(R.string.label_select_or_create_catalogue);

            getCatalogues();
        }

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                if (validation()) {
                    createCatalogue(binding.inputCatalogueTitle.getText().toString().trim());
                }
            }
        });

        binding.parentLinearLayout.setOnClickListener(v -> closeKeyboard());
    }

    @Override
    protected View getRootView() {
        return binding.getRoot();
    }

    private void getCatalogues() {
        progressDialogHandler.setProgress(true);
        DesignerLoginPreferences preferences = DesignerLoginPreferences.createInstance(this);
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
                        refreshSuggestions();
                    } else {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(this, throwable);
                    showMessage(binding.getRoot(), message);
                }));
    }

    private void refreshSuggestions() {
        String[] cataloguesArr = new String[catalogues.size()];
        for (int i = 0; i < catalogues.size(); i++) {
            cataloguesArr[i] = catalogues.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cataloguesArr);
        binding.inputCatalogueTitle.setAdapter(adapter);
    }

    private boolean validation() {
        String firstName = binding.inputCatalogueTitle.getText().toString().trim();
        if (firstName.isEmpty()) {
            binding.inputLayoutCatalogueTitle.setErrorEnabled(true);
            binding.inputLayoutCatalogueTitle.setError(getString(R.string.error_empty_catalogue));
            return false;
        } else {
            binding.inputLayoutCatalogueTitle.setErrorEnabled(false);
        }
        return true;
    }

    private void createCatalogue(String catalogueName) {
        progressDialogHandler.setProgress(true);
        DesignerLoginPreferences preferences = DesignerLoginPreferences.createInstance(this);
        int designerId = preferences.getDesignerId();
        Catalogue catalogue = new Catalogue(catalogueName, designerId);
        subscriptions.add(RestUtils.getAPIs().createCatalogue(preferences.getSessionToken(), catalogue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        if (response.getData() != null) {
                            openNextScreen(response.getData());
                        } else {
                            showMessage(binding.getRoot(), getString(R.string.server_error));
                        }
                    } else {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(this, throwable);
                    showMessage(binding.getRoot(), message);
                }));
    }

    private void openNextScreen(Catalogue catalogue) {
        startActivity(AddProductInfo1Activity.createIntent(this, catalogue));
    }
}
