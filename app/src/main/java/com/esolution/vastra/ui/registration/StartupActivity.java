package com.esolution.vastra.ui.registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastra.databinding.ActivityStartupBinding;
import com.esolution.vastrabasic.LanguageHelper;
import com.esolution.vastrabasic.ui.BaseActivity;
import com.esolution.vastrafashiondesigner.FashionDesignerHandler;

public class StartupActivity extends BaseActivity {

    private ActivityStartupBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartupActivity.this, RegisterUserTypeActivity.class);
                startActivity(intent);
            }
        });

        String savedLng = LanguageHelper.getLanguage(this);
        if (savedLng.equals("fr")) {
            binding.languageText.setText(com.esolution.vastrafashiondesigner.R.string.language_fr);
        }
        binding.languageLayout.setOnClickListener(v -> openLanguageDialog());
    }

    private void openLanguageDialog() {
        String[] languages = new String[2];
        languages[0] = getString(com.esolution.vastrafashiondesigner.R.string.language_en);
        languages[1] = getString(com.esolution.vastrafashiondesigner.R.string.language_fr);

        String savedLng = LanguageHelper.getLanguage(this);
        int index = savedLng.equals("fr") ? 1 : 0;

        new AlertDialog.Builder(this)
                .setSingleChoiceItems(languages, index, null)
                .setTitle(com.esolution.vastrafashiondesigner.R.string.select_language)
                .setPositiveButton(com.esolution.vastrafashiondesigner.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        binding.languageText.setText(languages[selectedPosition]);
                        changeLanguage(selectedPosition);
                    }
                }).show();
    }

    private void changeLanguage(int index) {
        LanguageHelper.changeLocale(this, index == 0 ? "en" : "fr");
        if (FashionDesignerHandler.getListener() != null) {
            FashionDesignerHandler.getListener().restartApp();
        }
    }
}
