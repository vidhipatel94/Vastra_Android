package com.esolution.vastra.ui.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastra.databinding.ActivityEmailVerificationBinding;
import com.esolution.vastrafashiondesigner.ui.startup.RegisterCreateCatalogueActivity;

public class EmailVerificationActivity extends AppCompatActivity {

    private ActivityEmailVerificationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    startActivity(new Intent(EmailVerificationActivity.this, RegisterCreateCatalogueActivity.class));
                    finish();
                };
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmailVerificationActivity.this,RegisterUserTypeActivity.class));
                finish();
            }
        });

        binding.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(EmailVerificationActivity.this);
            }
        });
    }

    private boolean validation() {
        if(binding.inputVerificationCode.getText().toString().isEmpty()) {
            Toast.makeText(this, "Verification code is mandatory.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void closeKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),0);
        }
    }
}
