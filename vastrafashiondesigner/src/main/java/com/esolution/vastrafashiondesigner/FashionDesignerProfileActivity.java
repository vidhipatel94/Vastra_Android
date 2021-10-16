package com.esolution.vastrafashiondesigner;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastrafashiondesigner.databinding.ActivityFashiondesignerProfileBinding;

public class FashionDesignerProfileActivity  extends AppCompatActivity {

    ActivityFashiondesignerProfileBinding binding;
    private static final String[] PROVINCES = new String[]{
            "Ontario", "New Brunswick", "Sasketchwan", "British Columbia", "Nova Scotia", "Quebec", "Alberta"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFashiondesignerProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarLayout.title.setText(R.string.title_profile);

        binding.inputProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProvinceDialog();
            }
        });

        binding.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(FashionDesignerProfileActivity.this);
            }
        });
    }

    private void openProvinceDialog() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(PROVINCES, 0, null)
                .setTitle(R.string.dialog_province_msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        binding.inputProvince.setText(PROVINCES[selectedPosition]);
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
