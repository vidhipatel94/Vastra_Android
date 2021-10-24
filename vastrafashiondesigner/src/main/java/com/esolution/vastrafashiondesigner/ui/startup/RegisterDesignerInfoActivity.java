package com.esolution.vastrafashiondesigner.ui.startup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.apis.request.RegisterDesignerRequest;
import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.databinding.ActivityRegisterDesignerInfoBinding;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterDesignerInfoActivity extends BaseActivity {

    private static final String EXTRA_USER = "extra_user";

    public static Intent createIntent(Context context, User user) {
        Intent intent = new Intent(context, RegisterDesignerInfoActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    private ActivityRegisterDesignerInfoBinding binding;
    private Designer designer;

    private ProgressDialogHandler progressDialogHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityRegisterDesignerInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogHandler = new ProgressDialogHandler(this);

        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.parentLinearLayout.setOnClickListener(v -> closeKeyboard());

        binding.btnCreateAccount.setOnClickListener(v -> onClickCreateAccount());
    }

    @Override
    protected View getRootView() {
        return binding.getRoot();
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            User user = (User) getIntent().getSerializableExtra(EXTRA_USER);
            if (user == null) {
                return false;
            }

            designer = new Designer(user.getEmail(), user.getPassword(), user.getFirstName(),
                    user.getLastName(), user.getAddress(), user.getCity(), user.getProvince(),
                    user.getPostalCode(), null);

            return true;
        }
        return false;
    }

    private void onClickCreateAccount() {
        closeKeyboard();
        if (isFormValidated()) {
            registerFashionDesigner();
        }
    }

    private boolean isFormValidated() {
        String brandName = binding.inputBrandName.getText().toString().trim();
        if (brandName.isEmpty()) {
            binding.inputLayoutBrandName.setErrorEnabled(true);
            binding.inputLayoutBrandName.setError(getString(R.string.error_empty_brand_name));
            return false;
        } else {
            binding.inputLayoutBrandName.setErrorEnabled(false);
        }

        String tagline = binding.inputTagline.getText().toString().trim();
        if (tagline.isEmpty()) {
            binding.inputLayoutTagline.setErrorEnabled(true);
            binding.inputLayoutTagline.setError(getString(R.string.error_empty_tagline));
            return false;
        } else {
            binding.inputLayoutTagline.setErrorEnabled(false);
        }

        designer.setBrandName(brandName);
        designer.setTagline(tagline);

        return true;
    }

    private void registerFashionDesigner() {
        progressDialogHandler.setProgress(true);
        RegisterDesignerRequest request = new RegisterDesignerRequest(this, designer.getEmail());
        request.setFirstName(designer.getFirstName());
        request.setLastName(designer.getLastName());
        request.setPassword(designer.getPassword());
        request.setAddress(designer.getAddress());
        request.setCity(designer.getCity());
        request.setProvince(designer.getProvince());
        request.setPostalCode(designer.getPostalCode());
        request.setBrandName(designer.getBrandName());
        request.setTagline(designer.getTagline());

        subscriptions.add(RestUtils.getAPIs().registerDesigner(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        boolean success = true;
                        if (response.getData() == null) {
                            success = false;
                        } else {
                            Designer designer = response.getData().getDesigner();
                            String sessionToken = response.getData().getSessionToken();
                            if (designer == null || TextUtils.isEmpty(sessionToken)) {
                                success = false;
                            } else {
                                saveDesignerData(designer, sessionToken);
                                openNextScreen();
                            }
                        }
                        if (!success) {
                            showMessage(binding.getRoot(), getString(R.string.server_error));
                        }
                    } else {
                        showMessage(binding.getRoot(), response.getMessage());
                    }
                }, throwable -> {
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(this, throwable);
                    showMessage(binding.getRoot(), message);
                }));
    }

    private void saveDesignerData(@NotNull Designer designer, @NotNull String sessionToken) {
        DesignerLoginPreferences.createInstance(this).login(designer, sessionToken);
    }

    private void openNextScreen() {
        Intent intent = new Intent(this, RegisterCreateCatalogueActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
