package com.esolution.vastrafashiondesigner.ui.newproduct.addcolor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivitySelectProductColorsBinding;
import com.esolution.vastrafashiondesigner.ui.newproduct.addsize.ProductSizesActivity;
import com.esolution.vastrafashiondesigner.ui.newproduct.addsize.SelectPrevAddedSizesDialog;

public class SelectProductColorsActivity extends AppCompatActivity {

    private ActivitySelectProductColorsBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectProductColorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setToolbarLayout();

        ProductColorAdapter adapter = new ProductColorAdapter();
        binding.colorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.colorsRecyclerView.setAdapter(adapter);

        binding.btnAdd.setOnClickListener((v) -> {
            openSelectColorDialog(1);
        });

        binding.btnNext.setOnClickListener((v) -> {
            Intent intent = new Intent(SelectProductColorsActivity.this, ProductSizesActivity.class);
            startActivity(intent);
        });
    }

    private void setToolbarLayout() {
        binding.toolbarLayout.iconBack.setOnClickListener((view) -> onBackPressed());
        binding.toolbarLayout.title.setText(R.string.title_available_colors);
    }

    private void openSelectColorDialog(int colorLevel) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SelectColorDialog dialog = SelectColorDialog.newInstance(colorLevel,
                new SelectColorDialog.SelectColorListener() {
                    @Override
                    public void onColorSelected() {
                        if (colorLevel < 3) {
                            openSelectColorDialog(colorLevel + 1);
                        }
                    }
                });
        dialog.show(fragmentManager, SelectPrevAddedSizesDialog.class.getName());
    }
}
