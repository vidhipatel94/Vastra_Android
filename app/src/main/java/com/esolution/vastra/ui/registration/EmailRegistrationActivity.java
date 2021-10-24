package com.esolution.vastra.ui.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.esolution.vastra.R;
import com.esolution.vastra.databinding.ActivityEmailRegistrationBinding;
import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrabasic.utils.Utils;

public class EmailRegistrationActivity extends BaseActivity {

    private static final String EXTRA_USER_TYPE = "extra_user_type";

    public static Intent createIntent(Context context, int userType) {
        Intent intent = new Intent(context, EmailRegistrationActivity.class);
        intent.putExtra(EXTRA_USER_TYPE, userType);
        return intent;
    }

    private ActivityEmailRegistrationBinding binding;
    private int userType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityEmailRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnNext.setOnClickListener(v -> {
            closeKeyboard();
            if (validation()) {
                openNextScreen();
            }
        });

        binding.btnBack.setOnClickListener(v -> onBackPressed());

        binding.rootLayout.setOnClickListener(v -> closeKeyboard());
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            userType = getIntent().getIntExtra(EXTRA_USER_TYPE, 0);
            return userType != 0;
        }
        return false;
    }

    @Override
    protected View getRootView() {
        return binding.rootLayout;
    }

    private boolean validation() {
        String email = binding.inputEmail.getText().toString().trim();
        if (email.isEmpty()) {
            binding.inputLayoutEmail.setErrorEnabled(true);
            binding.inputLayoutEmail.setError(getString(R.string.error_empty_email));
            return false;
        } else if (!Utils.isEmailAddressValid(email)) {
            binding.inputLayoutEmail.setErrorEnabled(true);
            binding.inputLayoutEmail.setError(getString(R.string.error_invalid_email));
            return false;
        }
        binding.inputLayoutEmail.setErrorEnabled(false);
        return true;
    }

    private void openNextScreen() {
        String email = binding.inputEmail.getText().toString().trim().toLowerCase();
        User user = new User(email, userType);
        startActivity(RegistrationFormActivity.createIntent(this, user));
    }
}
