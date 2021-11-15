package com.esolution.vastrafashiondesigner.ui.updateproduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductMaterial;
import com.esolution.vastrafashiondesigner.databinding.RowAddProductMaterialBinding;
import com.esolution.vastrafashiondesigner.ui.newproduct.AddProductInfo2Activity;

import java.util.List;

public class UpdateProductInfo2Activity extends AddProductInfo2Activity {

    public static Intent createIntent(Context context, Catalogue catalogue, Product product) {
        Intent intent = new Intent(context, UpdateProductInfo2Activity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fillData();
    }

    private void fillData() {
        binding.inputProductTitle.setText(product.getTitle());
        binding.inputProductPrice.setText(String.valueOf(product.getPrice()));
        binding.inputMultipackSet.setText(String.valueOf(product.getMultipackSet()));
        binding.inputProductWeight.setText(String.valueOf(product.getWeight()));

        List<ProductMaterial> materials = product.getMaterials();
        if (materials != null && !materials.isEmpty()) {
            ProductMaterial material1 = materials.get(0);
            binding.materialLayout1.autoCompleteProductMaterial.setText(material1.getMaterialName());
            binding.materialLayout1.inputProductPercentage.setText(String.valueOf(material1.getPercentage()));

            for (int i = 1; i < materials.size(); i++) {
                ProductMaterial material = materials.get(i);

                binding.linkAddMore.performClick();
                RowAddProductMaterialBinding materialBinding = materialBindingList.get(materialBindingList.size() - 1);
                materialBinding.autoCompleteProductMaterial.setText(material.getMaterialName());
                materialBinding.inputProductPercentage.setText(String.valueOf(material.getPercentage()));
            }
        }
    }

    @Override
    protected void openNextScreen() {
        startActivity(UpdateProductInfo3Activity.createIntent(this, catalogue, product));
    }
}
