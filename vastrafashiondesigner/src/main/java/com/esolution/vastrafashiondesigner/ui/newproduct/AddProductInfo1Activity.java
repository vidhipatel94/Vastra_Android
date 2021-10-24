package com.esolution.vastrafashiondesigner.ui.newproduct;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.R;
import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductInfo1Binding;

public class AddProductInfo1Activity extends BaseActivity {

    private static final String EXTRA_CATALOGUE = "extra_catalogue";

    public static Intent createIntent(Context context, Catalogue catalogue) {
        Intent intent = new Intent(context, AddProductInfo1Activity.class);
        intent.putExtra(EXTRA_CATALOGUE, catalogue);
        return intent;
    }

    private ActivityAddProductInfo1Binding binding;

    private Catalogue catalogue;

    private static final String[] PRODUCT_TYPES = new String[]{
            "Men's Shirt", "Men's Jeans", "Women's Top", "Womem's Bottom Wear", "Kid's Cloth", "Infant Cloth"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntentData()) {
            finish();
            return;
        }

        binding = ActivityAddProductInfo1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarLayout.title.setText(catalogue.getName());

        binding.inputProductType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductTypesDialog();
            }
        });

        binding.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(AddProductInfo1Activity.this);
            }
        });

        binding.toolbarLayout.iconBack.setOnClickListener(v -> onBackPressed());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProductInfo1Activity.this, AddProductInfo2Activity.class));
            }
        });
    }

    @Override
    protected View getRootView() {
        return binding.getRoot();
    }

    private boolean getIntentData() {
        if (getIntent() != null) {
            catalogue = (Catalogue) getIntent().getSerializableExtra(EXTRA_CATALOGUE);
            return catalogue != null;
        }
        return false;
    }

    private void openProductTypesDialog() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(PRODUCT_TYPES, 0, null)
                .setTitle(R.string.label_product_type)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        binding.inputProductType.setText(PRODUCT_TYPES[selectedPosition]);
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
