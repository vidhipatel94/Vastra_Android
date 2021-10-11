package com.esolution.vastrafashiondesigner.ui.newproduct.addcolor;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivitySelectColorBinding;

public class SelectColorActivity extends AppCompatActivity {

    private ActivitySelectColorBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectColorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarLayout.title.setText(R.string.title_prominent_color);
        binding.toolbarLayout.iconBack.setOnClickListener((view) -> onBackPressed());

        ColorAdapter adapter = new ColorAdapter();
        binding.colorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.colorsRecyclerView.setAdapter(adapter);


    }
}
