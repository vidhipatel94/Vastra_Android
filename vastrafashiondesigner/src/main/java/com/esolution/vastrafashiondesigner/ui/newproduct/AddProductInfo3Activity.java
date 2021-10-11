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
import com.esolution.vastrafashiondesigner.databinding.ActivityAddProductInfo3Binding;

public class AddProductInfo3Activity extends AppCompatActivity {

    private ActivityAddProductInfo3Binding binding;
    private static final String[] OCCASSION = new String[]{
            "Casual", "Formal", "Sports", "Ethnic"
    };
    private static final String[] PATTERN = new String[]{
            "Striped", "Checked", "Solid", "Printed","Color Blocked","Dyed","Self Design"
    };
    private static final String[] WASHCARE = new String[]{
            "Machine Wash", "Hand Wash", "Dry Clean"
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductInfo3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarLayout.title.setText(R.string.catalogue_name);

        binding.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(AddProductInfo3Activity.this);
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
                startActivity(new Intent(AddProductInfo3Activity.this,AddProductImagesActivity.class));
            }
        });

        binding.inputOccassion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOccassionDialog();
            }
        });

        binding.inputPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatternDialog();
            }
        });

        binding.inputWashCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWashcareDialog();
            }
        });
    }

    private void openOccassionDialog() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(OCCASSION, 0, null)
                .setTitle(R.string.select_occassion)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        binding.inputOccassion.setText(OCCASSION[selectedPosition]);
                    }
                }).show();
    }

    private void openPatternDialog() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(PATTERN, 0, null)
                .setTitle(R.string.select_pattern)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        binding.inputPattern.setText(PATTERN[selectedPosition]);
                    }
                }).show();
    }

    private void openWashcareDialog() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(WASHCARE, 0, null)
                .setTitle(R.string.select_washcare)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        binding.inputWashCare.setText(WASHCARE[selectedPosition]);
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
