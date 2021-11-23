package com.esolution.vastrafashiondesigner.ui.profile;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.esolution.vastrabasic.databinding.ActivityFashionDesignerProfileBinding;
import com.esolution.vastrafashiondesigner.databinding.RowCatalogueBinding;

public class FashionDesignerProfileActivity extends AppCompatActivity {
    ActivityFashionDesignerProfileBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFashionDesignerProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {
        int totalCatalogues = 5;

        binding.cataloguesLayout.removeAllViews();
        for(int i=0;i< totalCatalogues;i++) {
            RowCatalogueBinding rowCatalogueBinding = RowCatalogueBinding.inflate(getLayoutInflater());
            binding.cataloguesLayout.addView(rowCatalogueBinding.getRoot());
        }
    }
}
