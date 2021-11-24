package com.esolution.vastrashopper.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastrashopper.R;
import com.esolution.vastrashopper.databinding.ActivityProductCartBinding;

public class ProductCartActivity extends AppCompatActivity {
    private ActivityProductCartBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.myToolbar.title.setText(R.string.title_my_cart);

        binding.myToolbar.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNextScreen();
            }
        });
    }

    private void openNextScreen() {
        Intent intent = new Intent(this, ProductPlaceOrderActivity.class);
        startActivity(intent);
    }
}
