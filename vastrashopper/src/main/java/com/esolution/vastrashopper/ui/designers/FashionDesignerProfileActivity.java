package com.esolution.vastrashopper.ui.designers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrabasic.utils.ImageUtils;
import com.esolution.vastrashopper.databinding.ActivityFashionDesignerProfileBinding;

public class FashionDesignerProfileActivity extends AppCompatActivity {
    private ActivityFashionDesignerProfileBinding binding;
    private Designer designer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFashionDesignerProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentData();

        initView();
    }

    private void getIntentData() {
        if(getIntent().hasExtra("Designer")) {
            Intent data = getIntent();
            designer = (Designer) data.getSerializableExtra("Designer");
        }
    }

    private void initView() {
        int totalCatalogues = 5;

        binding.cataloguesLayout.removeAllViews();

        /*for (int i = 0; i < totalCatalogues; i++) {
            Log.i("-----", "initView: " + i + designer.getFirstName() + " " + designer.getLastName());
            RowCatalogueBinding rowCatalogueBinding = RowCatalogueBinding.inflate(getLayoutInflater());
            binding.cataloguesLayout.addView(rowCatalogueBinding.getRoot());
        }*/

        binding.toolbarLayout.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FashionDesignerProfileActivity.this, "I'm Clicked!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        ImageUtils.loadImageUrl(binding.imageProfilePic, designer.getAvatarURL());
        binding.fashionDesignerName.setText(designer.getFirstName() + " " + designer.getLastName());
        binding.brandName.setText(designer.getBrandName());
        binding.textTagLine.setText(designer.getTagline());
    }
}
