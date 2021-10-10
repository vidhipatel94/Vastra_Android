package com.esolution.vastrafashiondesigner.ui.startup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastrafashiondesigner.databinding.ActivityRegisterCreateCatalogueBinding;
import com.esolution.vastrafashiondesigner.ui.newproduct.AddProductInfo1Activity;

public class RegisterCreateCatalogueActivity extends AppCompatActivity {

    private ActivityRegisterCreateCatalogueBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterCreateCatalogueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()) {
                    startActivity(new Intent(RegisterCreateCatalogueActivity.this, AddProductInfo1Activity.class));
                }
            }
        });

        binding.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(RegisterCreateCatalogueActivity.this);
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
        if(binding.inputCatalogueTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter catalogue title.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
