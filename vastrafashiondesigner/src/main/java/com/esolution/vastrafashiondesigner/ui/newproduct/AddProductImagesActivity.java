package com.esolution.vastrafashiondesigner.ui.newproduct;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductImagesBinding;

public class AddProductImagesActivity extends AppCompatActivity {
    private ActivityAddProductImagesBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
