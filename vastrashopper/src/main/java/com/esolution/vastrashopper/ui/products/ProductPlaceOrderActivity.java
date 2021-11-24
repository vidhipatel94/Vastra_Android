package com.esolution.vastrashopper.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastrashopper.databinding.ActivityPlaceOrderBinding;
import com.esolution.vastrashopper.ui.ShopperMainActivity;

public class ProductPlaceOrderActivity extends AppCompatActivity {
    private ActivityPlaceOrderBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaceOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewScreen();
                finish();
            }
        });
    }

    private void openNewScreen() {
        Intent intent = new Intent(this, ShopperMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
