package com.esolution.vastrabasic.data;

import android.content.Context;
import android.content.SharedPreferences;

public class VastraBasicPreferences {
    private static final String PREFERENCES = "vastra_basic_preferences";

    private static final String KEY_UNIQUE_APP_INSTALL_ID = "key_unique_app_install_id";

    private final SharedPreferences sharedPreferences;

    // Singleton
    private static VastraBasicPreferences appPreferences;

    public static VastraBasicPreferences createInstance(Context context) {
        if (appPreferences == null) {
            appPreferences = new VastraBasicPreferences(context);
        }
        return appPreferences;
    }

    // private constructor
    private VastraBasicPreferences(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public String getUniqueAppInstallID() {
        return sharedPreferences.getString(KEY_UNIQUE_APP_INSTALL_ID, null);
    }

    public void setUniqueAppInstallID(String id) {
        sharedPreferences.edit().putString(KEY_UNIQUE_APP_INSTALL_ID, id).apply();
    }

}
