package com.esolution.vastrashopper.ui.products;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastrabasic.utils.ImageUtils;
import com.esolution.vastrashopper.databinding.ActivityFullProductImageViewBinding;

public class ProductFullImageViewActivity extends AppCompatActivity {
    private ActivityFullProductImageViewBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFullProductImageViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentData();

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIntentData() {
        if(getIntent().hasExtra("ProductImage")) {
            String imageUrl = getIntent().getStringExtra("ProductImage");
            ImageUtils.loadImageUrl(binding.fullImageView,imageUrl);
        }
    }
}
