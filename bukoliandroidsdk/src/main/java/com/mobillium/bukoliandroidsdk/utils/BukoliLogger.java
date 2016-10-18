package com.mobillium.bukoliandroidsdk.utils;

import android.util.Log;

import com.mobillium.bukoliandroidsdk.Bukoli;

/**
 * Created by oguzhandongul on 18/10/2016.
 */

public class BukoliLogger {
    private static String TAG = "BUKOLI_SDK";
    private static String TAG_PARAMS = "BUKOLI_SDK_PARAMS";

    public BukoliLogger() {
    }


    public static void writeErrorLog(String message) {
        if (Bukoli.getInstance().isDebugEnabled()) {
            Log.e(TAG, message);
        }
    }

    public static void writeInfoLog(String message) {
        if (Bukoli.getInstance().isDebugEnabled()) {
            Log.i(TAG, message);
        }
    }

    public static void writeResponseLog(String message) {
        if (Bukoli.getInstance().isDebugEnabled()) {
            Log.i(TAG_PARAMS, message);
        }
    }

    public static void writeDebugLog(String message) {
        if (Bukoli.getInstance().isDebugEnabled()) {
            Log.d(TAG, message);
        }
    }
}
