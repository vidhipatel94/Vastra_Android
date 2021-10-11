package com.esolution.vastrafashiondesigner.ui.newproduct.addcolor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivitySelectProductColorsBinding;

public class SelectProductColorsActivity extends AppCompatActivity {

    private ActivitySelectProductColorsBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectProductColorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarLayout.iconBack.setOnClickListener((view) -> onBackPressed());
        binding.toolbarLayout.title.setText(R.string.title_available_Colors);

        ProductColorAdapter adapter = new ProductColorAdapter();
        binding.colorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.colorsRecyclerView.setAdapter(adapter);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectProductColorsActivity.this, SelectColorActivity.class);
                startActivity(intent);
            }
        });
    }
}
