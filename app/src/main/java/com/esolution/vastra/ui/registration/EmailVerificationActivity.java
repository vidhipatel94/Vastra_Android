package com.esolution.vastra.ui.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastra.databinding.ActivityEmailVerificationBinding;

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
                    startActivity(new Intent(EmailVerificationActivity.this,RegistrationFormActivity.class));
                };
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
}
