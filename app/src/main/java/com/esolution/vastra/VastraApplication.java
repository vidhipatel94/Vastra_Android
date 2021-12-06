package com.esolution.vastra;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.esolution.vastra.ui.SplashScreenActivity;
import com.esolution.vastrabasic.data.VastraBasicPreferences;
import com.esolution.vastrabasic.utils.Utils;
import com.esolution.vastrafashiondesigner.FashionDesignerHandler;
import com.esolution.vastrashopper.ShopperHandler;

public class VastraApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();

        VastraBasicPreferences vastraBasicPreferences = VastraBasicPreferences.createInstance(context);
        if (vastraBasicPreferences.getUniqueAppInstallID() == null) {
            vastraBasicPreferences.setUniqueAppInstallID(Utils.generateUUID());
        }

        FashionDesignerHandler.instance(new FashionDesignerHandler.Listener() {
            @Override
            public void onLoggedOut() {
                reopenApp();
            }

            @Override
            public void restartApp() {
                reopenApp();
            }
        });

        ShopperHandler.instance(new ShopperHandler.Listener() {
            @Override
            public void onLoggedOut() {
                reopenApp();
            }

            @Override
            public void restartApp() {
                reopenApp();
            }
        });
    }

    private void reopenApp() {
        Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
