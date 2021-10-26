package com.esolution.vastra.ui.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastra.R;
import com.esolution.vastra.databinding.ActivityLoginBinding;
import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.apis.request.LoginRequest;
import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrabasic.utils.Utils;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.ui.MainActivity;
import com.esolution.vastrashopper.data.ShopperLoginPreferences;
import com.esolution.vastrashopper.ui.ShopperMainActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    private ProgressDialogHandler progressDialogHandler;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogHandler = new ProgressDialogHandler(this);

        binding.btnBack.setOnClickListener(v -> onBackPressed());

        binding.linkForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showMessage(binding.getRoot(), getString(R.string.todo));
            }
        });

        binding.parentLinearLayout.setOnClickListener(v -> closeKeyboard(LoginActivity.this));

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormValidated()){
                    userLogin();
                }
            }
        });
    }

    private boolean isFormValidated() {
        String email = binding.inputEmail.getText().toString().trim();
        if(email.isEmpty()) {
            binding.inputLayoutEmail.setErrorEnabled(true);
            binding.inputLayoutEmail.setError(getString(R.string.error_empty_email));
            return false;
        } else if(!Utils.isEmailAddressValid(email)) {
            binding.inputLayoutEmail.setErrorEnabled(true);
            binding.inputLayoutEmail.setError(getString(R.string.error_invalid_email));
            return false;
        } else {
            binding.inputLayoutEmail.setErrorEnabled(false);
        }

        String password = binding.inputPassword.getText().toString().trim();
        if (password.isEmpty()) {
            binding.inputLayoutPassword.setErrorEnabled(true);
            binding.inputLayoutPassword.setError(getString(R.string.error_empty_password));
            return false;
        } else {
            binding.inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private void userLogin() {
        progressDialogHandler.setProgress(true);
        String email = binding.inputEmail.getText().toString().trim();
        String password = binding.inputPassword.getText().toString().trim();

        LoginRequest loginRequest = new LoginRequest(this,email,password);
        subscriptions.add(RestUtils.getAPIs().login(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if(response.isSuccess()) {
                        boolean success = true;
                        if(response.getData() == null) {
                            success = false;
                        } else {
                            User user = response.getData().getUser();
                            Designer designer = response.getData().getDesigner();
                            String sessionToken = response.getData().getSessionToken();
                            if (user == null && designer != null && !TextUtils.isEmpty(sessionToken)) {
                                designerLogin(designer, sessionToken);
                                Log.i("Designer", "loginUser: ");
                                openDesignerHomeScreen();
                            } else if (user != null && designer == null && !TextUtils.isEmpty(sessionToken)) {
                                shopperLogin(user, sessionToken);
                                Log.i("Shopper", "loginUser: ");
                                openShopperHomeScreen();
                            } else {
                                success = false;
                            }
                            if (!success) {
                                showMessage(binding.getRoot(), getString(R.string.server_error));
                            }
                        }
                    } else {
                        showMessage(binding.getRoot(),response.getMessage());
                    }
                },throwable -> {
                    progressDialogHandler.setProgress(false);
                    String message = RestUtils.processThrowable(this,throwable);
                    showMessage(binding.getRoot(),message);
                }));

    }

    private void openShopperHomeScreen() {
        Intent intent = new Intent(this, ShopperMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void openDesignerHomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void shopperLogin(User user, String sessionToken) {
        ShopperLoginPreferences.createInstance(this).login(user,sessionToken);
    }

    private void designerLogin(Designer designer, String sessionToken) {
        DesignerLoginPreferences.createInstance(this).login(designer,sessionToken);
    }

    private void closeKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
