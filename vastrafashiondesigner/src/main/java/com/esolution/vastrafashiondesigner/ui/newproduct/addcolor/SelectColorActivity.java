package com.esolution.vastrafashiondesigner.ui.newproduct.addcolor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivitySelectColorBinding;

public class SelectColorActivity extends AppCompatActivity {

    private static final String EXTRA_COLOR_LEVEL = "extra_color_level";

    public static Intent createIntent(Context context, int colorLevel) {
        Intent intent = new Intent(context, SelectColorActivity.class);
        intent.putExtra(EXTRA_COLOR_LEVEL, colorLevel);
        return intent;
    }

    private ActivitySelectColorBinding binding;
    private int colorLevel = 1;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectColorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        colorLevel = getIntent().getIntExtra(EXTRA_COLOR_LEVEL, 1);

        setToolbarLayout();

        ColorAdapter adapter = new ColorAdapter();
        binding.colorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.colorsRecyclerView.setAdapter(adapter);

        if (colorLevel == 3) binding.btnNext.setText(R.string.btn_done);
        binding.btnNext.setOnClickListener((v -> onClickNext()));
    }

    private void setToolbarLayout() {
        int titleText;
        switch (colorLevel) {
            case 2:
                titleText = R.string.title_secondary_color;
                break;
            case 3:
                titleText = R.string.title_tertiary_color;
                break;
            default:
                titleText = R.string.title_prominent_color;
                break;
        }
        binding.toolbarLayout.title.setText(titleText);
        binding.toolbarLayout.iconBack.setOnClickListener((view) -> onBackPressed());
    }

    private void onClickNext() {
        if (colorLevel < 3) {
            startActivity(SelectColorActivity.createIntent(this, colorLevel + 1));
        }
        finish();
    }
}
