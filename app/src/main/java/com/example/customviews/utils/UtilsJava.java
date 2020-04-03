package com.example.customviews.utils;

import android.content.res.Resources;

public class UtilsJava {

    public static int pixelsToDp(int pixels) {
        return (int) (pixels / Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }

    public static int dpToPixels(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }
}
