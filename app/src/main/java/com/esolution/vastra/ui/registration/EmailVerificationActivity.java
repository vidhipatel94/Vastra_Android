package com.esolution.vastra.ui.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.esolution.vastra.databinding.ActivityEmailVerificationBinding;
import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrabasic.utils.Utils;

public class EmailVerificationActivity extends BaseActivity {

    private static final String EXTRA_USER = "extra_user";

    public static Intent createIntent(Context context, User user) {
        Intent intent = new Intent(context, EmailVerificationActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    private ActivityEmailVerificationBinding binding;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityEmailVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                if (validation()) {
                    startActivity(new Intent(EmailVerificationActivity.this, RegistrationFormActivity.class));
                    finish();
                }
            }
        });

        binding.btnBack.setOnClickListener(v -> onBackPressed());

        binding.parentLinearLayout.setOnClickListener(v -> closeKeyboard());
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            user = (User) getIntent().getSerializableExtra(EXTRA_USER);
            return user != null && !TextUtils.isEmpty(user.getEmail());
        }
        return false;
    }

    private boolean validation() {
        if (binding.inputVerificationCode.getText().toString().isEmpty()) {
            Toast.makeText(this, "Verification code is mandatory.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
