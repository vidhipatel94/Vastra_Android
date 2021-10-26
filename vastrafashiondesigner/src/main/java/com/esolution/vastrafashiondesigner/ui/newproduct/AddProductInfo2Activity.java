package com.esolution.vastrafashiondesigner.ui.newproduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductInfo2Binding;
import com.esolution.vastrafashiondesigner.databinding.RowAddProductMaterialBinding;

public class AddProductInfo2Activity extends BaseActivity {

    private static final String EXTRA_CATALOGUE = "extra_catalogue";
    private static final String EXTRA_PRODUCT = "extra_product";

    public static Intent createIntent(Context context, Catalogue catalogue, Product product) {
        Intent intent = new Intent(context, AddProductInfo2Activity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    private ActivityAddProductInfo2Binding binding;

    private Catalogue catalogue;
    private Product product;

    private static final String[] MATERIALS = new String[]{
            "Cotton", "Jute", "Polyster", "Linen", "Viscose", "Wool", "Leather"
    };
    private ArrayAdapter<String> materialAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityAddProductInfo2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    @Override
    protected View getRootView() {
        return binding.getRoot();
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            catalogue = (Catalogue) getIntent().getSerializableExtra(EXTRA_CATALOGUE);
            product = (Product) getIntent().getSerializableExtra(EXTRA_PRODUCT);
            return catalogue != null && product != null;
        }
        return false;
    }

    private void initView() {
        binding.toolbarLayout.title.setText(catalogue.getName());
        binding.toolbarLayout.iconBack.setOnClickListener(v -> onBackPressed());

        binding.parentLinearLayout.setOnClickListener(v -> closeKeyboard());

        materialAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MATERIALS);
        binding.autoCompleteProductMaterial.setAdapter(materialAdapter);

        binding.linkAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RowAddProductMaterialBinding materialBinding = RowAddProductMaterialBinding.inflate(getLayoutInflater());
                binding.addMoreLinearLayout.addView(materialBinding.getRoot());

                materialBinding.iconDelete.setOnClickListener((v1) -> {
                    binding.addMoreLinearLayout.removeView(materialBinding.getRoot());
                });
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProductInfo2Activity.this, AddProductInfo3Activity.class));
            }
        });
    }

}
