package com.esolution.vastra.ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastra.databinding.ActivityRegistrationFormBinding;

public class RegistrationFormActivity extends AppCompatActivity {

    private ActivityRegistrationFormBinding binding;
    private static final String[] PROVINCES = new String[] {
            "Ontario","New Brunswick","Sasketchwan","British Columbia", "Nova Scotia", "Quebec","Alberta"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationFormActivity.this,EmailVerificationFormActivity.class));
                finish();
            }
        });

        binding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        fillAutoCompleteTextView();
    }

    private void fillAutoCompleteTextView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, PROVINCES);
        binding.inputProvince.setAdapter(adapter);
    }
}
