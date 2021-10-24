package com.esolution.vastra.ui.registration;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.esolution.vastra.R;
import com.esolution.vastra.databinding.ActivityRegistrationFormBinding;
import com.esolution.vastrabasic.ProgressDialogHandler;
import com.esolution.vastrabasic.apis.RestUtils;
import com.esolution.vastrabasic.apis.request.RegisterShopperRequest;
import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.models.UserType;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrabasic.utils.Utils;
import com.esolution.vastrafashiondesigner.ui.startup.RegisterDesignerInfoActivity;
import com.esolution.vastrashopper.data.ShopperLoginPreferences;
import com.esolution.vastrashopper.ui.ShopperMainActivity;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegistrationFormActivity extends BaseActivity {

    private static final String EXTRA_USER = "extra_user";

    public static Intent createIntent(Context context, User user) {
        Intent intent = new Intent(context, RegistrationFormActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    private ActivityRegistrationFormBinding binding;
    private String[] provinces;
    private int selectedProvincePosition = -1;

    private User user;

    private ProgressDialogHandler progressDialogHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityRegistrationFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        provinces = getResources().getStringArray(R.array.provinces);
        progressDialogHandler = new ProgressDialogHandler(this);

        binding.btnBack.setOnClickListener(v -> onBackPressed());
        binding.parentLinearLayout.setOnClickListener(v -> closeKeyboard());

        binding.inputProvince.setOnClickListener(v -> openProvinceDialog());

        if (user.getType() == UserType.FashionDesigner.getValue()) {
            binding.btnCreateAccount.setText(R.string.btn_next);
        }
        binding.btnCreateAccount.setOnClickListener(v -> onClickCreateAccount());
    }

    @Override
    protected View getRootView() {
        return binding.getRoot();
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            user = (User) getIntent().getSerializableExtra(EXTRA_USER);
            return user != null && !TextUtils.isEmpty(user.getEmail());
        }
        return false;
    }

    private void openProvinceDialog() {
        closeKeyboard();
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(provinces, Math.max(selectedProvincePosition, 0), null)
                .setTitle(R.string.dialog_province_msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        selectedProvincePosition = selectedPosition;
                        binding.inputProvince.setText(provinces[selectedPosition]);
                    }
                }).show();
    }

    private void onClickCreateAccount() {
        closeKeyboard();
        if (isFormValidated()) {
            if (user.getType() == UserType.Shopper.getValue()) {
                registerShopper();
            } else {
                startActivity(RegisterDesignerInfoActivity.createIntent(this, user));
            }
        }
    }

    private boolean isFormValidated() {
        String firstName = binding.inputFirstName.getText().toString().trim();
        if (firstName.isEmpty()) {
            binding.inputLayoutFirstName.setErrorEnabled(true);
            binding.inputLayoutFirstName.setError(getString(R.string.error_empty_first_name));
            return false;
        } else {
            binding.inputLayoutFirstName.setErrorEnabled(false);
        }

        String lastName = binding.inputLastName.getText().toString().trim();
        if (lastName.isEmpty()) {
            binding.inputLayoutLastName.setErrorEnabled(true);
            binding.inputLayoutLastName.setError(getString(R.string.error_empty_last_name));
            return false;
        } else {
            binding.inputLayoutLastName.setErrorEnabled(false);
        }

        String password = binding.inputPassword.getText().toString().trim();
        if (password.isEmpty()) {
            binding.inputLayoutPassword.setErrorEnabled(true);
            binding.inputLayoutPassword.setError(getString(R.string.error_empty_password));
            return false;
        } else {
            binding.inputLayoutPassword.setErrorEnabled(false);
        }

        String address = binding.inputAddress.getText().toString().trim();
        if (address.isEmpty()) {
            binding.inputLayoutAddress.setErrorEnabled(true);
            binding.inputLayoutAddress.setError(getString(R.string.error_empty_address));
            return false;
        } else {
            binding.inputLayoutAddress.setErrorEnabled(false);
        }

        if (selectedProvincePosition == -1) {
            binding.inputLayoutProvince.setErrorEnabled(true);
            binding.inputLayoutProvince.setError(getString(R.string.error_empty_province));
            return false;
        } else {
            binding.inputLayoutProvince.setErrorEnabled(false);
        }

        String city = binding.inputCity.getText().toString().trim();
        if (city.isEmpty()) {
            binding.inputLayoutCity.setErrorEnabled(true);
            binding.inputLayoutCity.setError(getString(R.string.error_empty_city));
            return false;
        } else {
            binding.inputLayoutCity.setErrorEnabled(false);
        }

        String postalCode = binding.inputPostalCode.getText().toString().trim();
        if (postalCode.isEmpty()) {
            binding.inputLayoutPostalCode.setErrorEnabled(true);
            binding.inputLayoutPostalCode.setError(getString(R.string.error_empty_postal_code));
            return false;
        } else if (!Utils.isPostalCodeValid(postalCode)) {
            binding.inputLayoutPostalCode.setErrorEnabled(true);
            binding.inputLayoutPostalCode.setError(getString(R.string.error_invalid_postal_code));
            return false;
        } else {
            binding.inputLayoutPostalCode.setErrorEnabled(false);
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setAddress(address);
        user.setCity(city);
        user.setProvince(provinces[selectedProvincePosition]);
        user.setPostalCode(postalCode);

        return true;
    }

    private void registerShopper() {
        progressDialogHandler.setProgress(true);
        RegisterShopperRequest request = new RegisterShopperRequest(this, user.getEmail());
        request.setFirstName(user.getFirstName());
        request.setLastName(user.getLastName());
        request.setPassword(user.getPassword());
        request.setAddress(user.getAddress());
        request.setCity(user.getCity());
        request.setProvince(user.getProvince());
        request.setPostalCode(user.getPostalCode());

        subscriptions.add(RestUtils.getAPIs().registerShopper(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    progressDialogHandler.setProgress(false);
                    if (response.isSuccess()) {
                        boolean success = true;
                        if (response.getData() == null) {
                            success = false;
                        } else {
                            User user = response.getData().getUser();
                            String sessionToken = response.getData().getSessionToken();
                            if (user == null || TextUtils.isEmpty(sessionToken)) {
                                success = false;
                            } else {
                                saveShopperData(user, sessionToken);
                                openShopperHomeScreen();
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

    private void saveShopperData(@NotNull User user, @NotNull String sessionToken) {
        ShopperLoginPreferences.createInstance(this).login(user, sessionToken);
    }

    private void openShopperHomeScreen() {
        Intent intent = new Intent(this, ShopperMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
