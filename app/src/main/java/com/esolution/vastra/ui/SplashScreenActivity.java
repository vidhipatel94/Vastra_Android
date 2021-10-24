package com.esolution.vastra.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.esolution.vastra.R;
import com.esolution.vastra.ui.registration.StartupActivity;
import com.esolution.vastrafashiondesigner.data.DesignerLoginPreferences;
import com.esolution.vastrafashiondesigner.ui.MainActivity;
import com.esolution.vastrashopper.data.ShopperLoginPreferences;
import com.esolution.vastrashopper.ui.ShopperMainActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashScreenActivity.this.runOnUiThread(() -> {
                    openNextScreen();
                });
            }
        }, 2000);
    }

    private void openNextScreen() {
        ShopperLoginPreferences shopperLoginPreferences = ShopperLoginPreferences.createInstance(this);
        if (shopperLoginPreferences.isLoggedIn()) {
            Intent intent = new Intent(this, ShopperMainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        DesignerLoginPreferences designerLoginPreferences = DesignerLoginPreferences.createInstance(this);
        if (designerLoginPreferences.isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        Intent intent = new Intent(this, StartupActivity.class);
        startActivity(intent);
        finish();
    }
}
