package com.esolution.vastrabasic.ui.customorder;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.esolution.vastrabasic.R;
import com.esolution.vastrabasic.databinding.ActivityCustomOrderBinding;
import com.esolution.vastrabasic.ui.BaseActivity;

public class CustomOrderActivity extends BaseActivity {

    private ActivityCustomOrderBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomOrderBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        binding.toolbarLayout.iconBack.setOnClickListener(v -> onBackPressed());

        binding.btnCreateCustomOrder.setOnClickListener(v -> {
            showMessage(binding.getRoot(), getString(R.string.not_implemented));
        });
    }
}
