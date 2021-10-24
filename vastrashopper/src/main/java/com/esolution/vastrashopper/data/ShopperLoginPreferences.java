package com.esolution.vastrashopper.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.models.UserType;
import com.esolution.vastrabasic.utils.JsonUtils;

public class ShopperLoginPreferences {
    private static final String PREFERENCES = "vastra_user_preferences";

    private static final String KEY_LOGGED_IN_USER = "key_logged_in_user";
    private static final String KEY_LOGGED_IN_USER_TYPE = "key_logged_in_user_type";
    private static final String KEY_SESSION_TOKEN = "key_session_token";

    private final SharedPreferences sharedPreferences;

    // Singleton
    private static ShopperLoginPreferences appPreferences;

    public static ShopperLoginPreferences createInstance(Context context) {
        if (appPreferences == null) {
            appPreferences = new ShopperLoginPreferences(context);
        }
        return appPreferences;
    }

    // private constructor
    private ShopperLoginPreferences(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public void login(@NonNull User user, @NonNull String sessionToken) {
        sharedPreferences.edit().putString(KEY_LOGGED_IN_USER, JsonUtils.toJson(user)).apply();
        sharedPreferences.edit().putInt(KEY_LOGGED_IN_USER_TYPE, user.getType()).apply();
        sharedPreferences.edit().putString(KEY_SESSION_TOKEN, sessionToken).apply();
    }

    public User getShopper() {
        String jsonStr = sharedPreferences.getString(KEY_LOGGED_IN_USER, null);
        return JsonUtils.fromJson(jsonStr, User.class);
    }

    public String getSessionToken() {
        return sharedPreferences.getString(KEY_SESSION_TOKEN, null);
    }

    public void logout() {
        sharedPreferences.edit().putString(KEY_LOGGED_IN_USER, null).apply();
        sharedPreferences.edit().putString(KEY_SESSION_TOKEN, null).apply();
    }

    public int getLoggedInUserType() {
        return sharedPreferences.getInt(KEY_LOGGED_IN_USER_TYPE, 0);
    }

    public boolean isLoggedIn() {
        return getShopper() != null
                && !TextUtils.isEmpty(getSessionToken())
                && getLoggedInUserType() == UserType.Shopper.getValue();
    }
}
