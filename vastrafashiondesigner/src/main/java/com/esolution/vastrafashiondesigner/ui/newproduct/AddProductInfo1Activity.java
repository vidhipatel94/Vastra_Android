package com.esolution.vastrafashiondesigner.ui.newproduct;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductInfo1Binding;

public class AddProductInfo1Activity extends AppCompatActivity {

    private ActivityAddProductInfo1Binding binding;
    private static final String[] PRODUCT_TYPES = new String[]{
            "Men's Shirt", "Men's Jeans", "Women's Top", "Womem's Bottom Wear", "Kid's Cloth", "Infant Cloth"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductInfo1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarLayout.title.setText(R.string.catalogue_name);

        binding.inputProductType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProvinceDialog();
            }
        });

        binding.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(AddProductInfo1Activity.this);
            }
        });

        binding.toolbarLayout.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProductInfo1Activity.this,AddProductInfo2Activity.class));
            }
        });
    }

    private void openProvinceDialog() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(PRODUCT_TYPES, 0, null)
                .setTitle(R.string.title_product_alert_dialog)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                    }
                }).show();
    }

    private void closeKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
