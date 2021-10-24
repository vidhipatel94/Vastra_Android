package com.esolution.vastrabasic.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class JsonUtils {
    private static final Gson GSON = new Gson();

    private JsonUtils() {
        throw new AssertionError("No Instances");
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return GSON.fromJson(json, type);
        } catch (Exception e) {
            Log.e("Json Parse Error", e.toString());
            return null;
        }
    }

    public static <T> T fromJson(String json, TypeToken<T> typeToken) {
        try {
            return GSON.fromJson(json, typeToken.getType());
        } catch (Exception e) {
            Log.e("Json Parse Error", e.toString());
            return null;
        }
    }

    public static <T> T fromJson(JsonElement jsonElement, Type typeOfT) {
        try {
            return GSON.fromJson(jsonElement, typeOfT);
        } catch (Exception e) {
            Log.e("Json Parse Error", e.toString());
            return null;
        }
    }
}

