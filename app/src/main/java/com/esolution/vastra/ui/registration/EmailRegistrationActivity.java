package com.esolution.vastra.ui.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastra.databinding.ActivityEmailRegistrationBinding;

public class EmailRegistrationActivity extends AppCompatActivity {

    private ActivityEmailRegistrationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    startActivity(new Intent(EmailRegistrationActivity.this,RegistrationFormActivity.class));
                };
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmailRegistrationActivity.this,RegisterUserTypeActivity.class));
                finish();
            }
        });

        binding.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(EmailRegistrationActivity.this);
            }
        });
    }

    private void closeKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),0);
        }
    }

    private boolean validation() {
        if(binding.inputEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Email address is mandatory.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
