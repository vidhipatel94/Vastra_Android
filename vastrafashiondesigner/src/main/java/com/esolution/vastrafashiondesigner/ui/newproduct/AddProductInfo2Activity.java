package com.esolution.vastrafashiondesigner.ui.newproduct;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductInfo2Binding;

public class AddProductInfo2Activity extends AppCompatActivity {

    private ActivityAddProductInfo2Binding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductInfo2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
