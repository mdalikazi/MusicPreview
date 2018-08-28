package com.alikazi.codetestaim.utils;

import android.util.Log;

import com.alikazi.codetestaim.BuildConfig;

public class DLog {

    private static final boolean mIsLogcatEnabled = BuildConfig.DEBUG;

    public static void wtf(String tag, String msg) {
        if (mIsLogcatEnabled) {
            Log.wtf(tag, msg);
        }
    }

    public static void e(String tag, String msg, Exception e) {
        if (mIsLogcatEnabled) {
            if (e != null) {
                Log.e(tag, getTextWithSource(msg), e);
            } else {
                Log.e(tag, getTextWithSource(msg));
            }
        }
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    public static void w(String tag, String msg) {
        if (mIsLogcatEnabled) {
            Log.w(tag, getTextWithSource(msg));
        }
    }

    public static void i(String tag, String msg) {
        if (mIsLogcatEnabled) {
            Log.i(tag, getTextWithSource(msg));
        }
    }

    public static void d(String tag, String msg) {
        if (mIsLogcatEnabled) {
            Log.d(tag, getTextWithSource(msg));
        }
    }

    public static void v(String tag, String msg) {
        if (mIsLogcatEnabled) {
            Log.v(tag, getTextWithSource(msg));
        }
    }

    private static String getTextWithSource(String text) {
        return "<" + getSourceCodeFileName() + ":" + getLineNumber() + "> " + text;
    }

    private static String getSourceCodeFileName() {
        return Thread.currentThread().getStackTrace()[5].getFileName();
    }

    private static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[5].getLineNumber();
    }

}
