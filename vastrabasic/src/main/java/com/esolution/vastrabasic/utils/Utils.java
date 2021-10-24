package com.esolution.vastrabasic.utils;

import static android.content.Context.TELEPHONY_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.esolution.vastrabasic.data.VastraBasicPreferences;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static boolean isEmailAddressValid(String emailAddress) {
        Pattern pattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }

    public static boolean isPostalCodeValid(String postalCode) {
        Pattern pattern = Pattern.compile("^[A-Za-z][0-9][A-Za-z][\\s]?[0-9][A-Za-z][0-9]$");
        Matcher matcher = pattern.matcher(postalCode);
        return matcher.matches();
    }

    public static boolean isActivityExists(Activity activity) {
        if (activity == null) return false;
        return !activity.isFinishing() && !activity.isDestroyed();
    }
}
