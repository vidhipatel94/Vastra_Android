package com.esolution.vastrafashiondesigner.utils;

import android.content.res.Resources;

public class Utils {
    public static int getPixels(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
