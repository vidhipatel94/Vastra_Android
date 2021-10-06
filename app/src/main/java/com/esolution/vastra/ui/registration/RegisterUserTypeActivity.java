package com.esolution.vastra.ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastra.databinding.ActivityRegisterUserTypeBinding;

public class RegisterUserTypeActivity extends AppCompatActivity {

    private ActivityRegisterUserTypeBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterUserTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener((View v) -> {
            startActivity(new Intent(RegisterUserTypeActivity.this,StartupActivity.class));
            finish();
        });

        binding.btnNextFashionDesigner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterUserTypeActivity.this,EmailVerificationFormActivity.class));
            }
        });

        binding.btnNextShopper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterUserTypeActivity.this,EmailVerificationFormActivity.class));
            }
        });
    }
}
