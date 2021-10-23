package com.esolution.vastrabasic.utils;

import static android.content.Context.TELEPHONY_SERVICE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;

import com.esolution.vastrabasic.data.VastraBasicPreferences;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Utils {
    public static int getPixels(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static void showMessage(View rootView, String message) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(@NotNull Context context) {
        String deviceId;
        try {
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            deviceId = TelephonyMgr.getDeviceId();
        } catch (SecurityException e) {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        if (deviceId == null) {
            deviceId = VastraBasicPreferences.createInstance(context).getUniqueAppInstallID();
        }
        if (deviceId == null) {
            deviceId = generateUUID();
            VastraBasicPreferences.createInstance(context).setUniqueAppInstallID(deviceId);
        }
        return deviceId;
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
