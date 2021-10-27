package com.esolution.vastrafashiondesigner.ui.startup;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrabasic.utils.JsonUtils;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.databinding.ActivityRegisterCreateCatalogueBinding;
import com.esolution.vastrafashiondesigner.ui.newproduct.AddProductInfo1Activity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterCreateCatalogueActivity extends BaseActivity {

    private ActivityRegisterCreateCatalogueBinding binding;
    private ProgressDialogHandler progressDialogHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterCreateCatalogueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogHandler = new ProgressDialogHandler(this);

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    Log.d("-----", "createCatalogue: "+ JsonUtils.toJson(response));
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
