package com.esolution.vastrafashiondesigner.ui.newproduct;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductInfo2Binding;
import com.esolution.vastrafashiondesigner.databinding.RowAddProductMaterialBinding;

public class AddProductInfo2Activity extends AppCompatActivity {

    private static final String EXTRA_CATALOGUE = "extra_catalogue";
    private static final String EXTRA_PRODUCT = "extra_product";

    public static Intent createIntent(Context context, Catalogue catalogue, Product product) {
        Intent intent = new Intent(context, AddProductInfo2Activity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        intent.putExtra(EXTRA_PRODUCT, product);
        return intent;
    }

    private ActivityAddProductInfo2Binding binding;
    private static final String[] MATERIALS = new String[]{
            "Cotton", "Jute", "Polyster", "Linen", "Viscose", "Wool", "Leather"
    };
    ArrayAdapter<String> materialAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductInfo2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarLayout.title.setText(R.string.catalogue_name);

        binding.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(AddProductInfo2Activity.this);
            }
        });

        materialAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,MATERIALS);
        binding.autoCompleteProductMaterial.setAdapter(materialAdapter);

        binding.toolbarLayout.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProductInfo2Activity.this,AddProductInfo3Activity.class));
            }
        });

        binding.linkAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RowAddProductMaterialBinding materialBinding = RowAddProductMaterialBinding.inflate(getLayoutInflater());
                binding.addMoreLinearLayout.addView(materialBinding.getRoot());

                materialBinding.iconDelete.setOnClickListener((v1)-> {
                    binding.addMoreLinearLayout.removeView(materialBinding.getRoot());
                });
            }
        });
    }

    private void closeKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
