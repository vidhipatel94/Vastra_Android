package com.esolution.vastrashopper.ui.products;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastrabasic.models.product.BasicProduct;
import com.esolution.vastrabasic.utils.ImageUtils;
import com.esolution.vastrashopper.databinding.ActivityProductDetailsBinding;

public class ProductDetailsActivity extends AppCompatActivity {
    private ActivityProductDetailsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());

        getIntentData();

        setContentView(binding.getRoot());
    }

    private void getIntentData() {
        Intent intent = getIntent();

        if(intent.hasExtra("Product")){
            BasicProduct basicProduct = (BasicProduct) intent.getSerializableExtra("Product");
            setProductData(basicProduct);
        }
    }

    private void setProductData(BasicProduct basicProduct) {
        ImageUtils.loadImageUrl(binding.productImages, basicProduct.getImages().get(0));
        binding.designerName.setText("By " + basicProduct.getDesignerName());
        binding.title.setText(basicProduct.getTitle());
        binding.rating.setText(String.valueOf(basicProduct.getOverallRating()));
        binding.price.setText(String.valueOf(basicProduct.getPrice()));
        binding.totalLikes.setText(String.valueOf(basicProduct.getTotalLikes()) + " Likes");
    }
}
