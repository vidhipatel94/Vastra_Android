package com.esolution.vastra.ui.registration;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastra.databinding.ActivityRegisterUserTypeBinding;
import com.esolution.vastrabasic.models.UserType;

public class RegisterUserTypeActivity extends AppCompatActivity {

    private ActivityRegisterUserTypeBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterUserTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener((View v) -> onBackPressed());

        binding.layoutFashionDesigner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(EmailRegistrationActivity.createIntent(RegisterUserTypeActivity.this,
                        UserType.FashionDesigner.getValue()));
            }
        });

        binding.layoutShopper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(EmailRegistrationActivity.createIntent(RegisterUserTypeActivity.this,
                        UserType.Shopper.getValue()));
            }
        });
    }
}
