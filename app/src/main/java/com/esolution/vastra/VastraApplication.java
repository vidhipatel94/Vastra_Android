package com.esolution.vastra;

import android.app.Application;
import android.content.Context;

import com.esolution.vastrabasic.data.VastraBasicPreferences;
import com.esolution.vastrabasic.utils.Utils;

public class VastraApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();

        VastraBasicPreferences vastraBasicPreferences = VastraBasicPreferences.createInstance(context);
        if (vastraBasicPreferences.getUniqueAppInstallID() == null) {
            vastraBasicPreferences.setUniqueAppInstallID(Utils.generateUUID());
        }
    }
}
