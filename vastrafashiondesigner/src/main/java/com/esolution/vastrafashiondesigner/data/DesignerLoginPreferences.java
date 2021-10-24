package com.esolution.vastrafashiondesigner.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrabasic.models.UserType;
import com.esolution.vastrabasic.utils.JsonUtils;

import org.jetbrains.annotations.NotNull;

public class DesignerLoginPreferences {
    private static final String PREFERENCES = "vastra_user_preferences";

    private static final String KEY_LOGGED_IN_USER = "key_logged_in_user";
    private static final String KEY_LOGGED_IN_USER_TYPE = "key_logged_in_user_type";
    private static final String KEY_SESSION_TOKEN = "key_session_token";

    private static final String KEY_IS_ANY_CATALOGUE_ADDED = "key_is_any_catalogue_added";

    private final SharedPreferences sharedPreferences;

    // Singleton
    private static DesignerLoginPreferences appPreferences;

    public static DesignerLoginPreferences createInstance(Context context) {
        if (appPreferences == null) {
            appPreferences = new DesignerLoginPreferences(context);
        }
        return appPreferences;
    }

    // private constructor
    private DesignerLoginPreferences(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public void login(@NotNull Designer designer, @NotNull String sessionToken) {
        sharedPreferences.edit().putString(KEY_LOGGED_IN_USER, JsonUtils.toJson(designer)).apply();
        sharedPreferences.edit().putInt(KEY_LOGGED_IN_USER_TYPE, designer.getType()).apply();
        sharedPreferences.edit().putString(KEY_SESSION_TOKEN, sessionToken).apply();
    }

    public Designer getDesigner() {
        String jsonStr = sharedPreferences.getString(KEY_LOGGED_IN_USER, null);
        return JsonUtils.fromJson(jsonStr, Designer.class);
    }

    public String getSessionToken() {
        return sharedPreferences.getString(KEY_SESSION_TOKEN, null);
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LOGGED_IN_USER, null);
        editor.putInt(KEY_LOGGED_IN_USER_TYPE, 0);
        editor.putString(KEY_SESSION_TOKEN, null);
        editor.putBoolean(KEY_IS_ANY_CATALOGUE_ADDED, false);
        editor.apply();
    }

    public int getLoggedInUserType() {
        return sharedPreferences.getInt(KEY_LOGGED_IN_USER_TYPE, 0);
    }

    public boolean isLoggedIn() {
        return getDesigner() != null
                && !TextUtils.isEmpty(getSessionToken())
                && getLoggedInUserType() == UserType.FashionDesigner.getValue();
    }

    public void setAnyCatalogueAdded(boolean isAdded) {
        sharedPreferences.edit().putBoolean(KEY_IS_ANY_CATALOGUE_ADDED, isAdded).apply();
    }

    public boolean isAnyCatalogueAdded() {
        return sharedPreferences.getBoolean(KEY_IS_ANY_CATALOGUE_ADDED, false);
    }

}
