package com.code.cframe.utils;

import java.io.Serializable;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

public class IntentUtils {

    public static boolean getBooleanExtra(Intent intent, String key) {
        if (intent == null || TextUtils.isEmpty(key)) {
            return false;
        }
        return intent.getBooleanExtra(key, false);
    }

    public static String getStringExtra(Intent intent, String key) {
        if (intent == null || TextUtils.isEmpty(key)) {
            return null;
        }
        return intent.getStringExtra(key);
    }

    public static int getIntExtra(Intent intent, String key) {
        if (intent == null || TextUtils.isEmpty(key)) {
            return -1;
        }
        return intent.getIntExtra(key, -1);
    }

    public static Serializable getSerializableExtra(Intent intent, String key) {
        if (intent == null || TextUtils.isEmpty(key)) {
            return null;
        }
        return intent.getSerializableExtra(key);
    }


    public static boolean getBooleanExtra(Bundle intent, String key) {
        if (intent == null || TextUtils.isEmpty(key)) {
            return false;
        }
        return intent.getBoolean(key, false);
    }

    public static String getStringExtra(Bundle intent, String key) {
        if (intent == null || TextUtils.isEmpty(key)) {
            return null;
        }
        return intent.getString(key);
    }

    public static int getIntExtra(Bundle intent, String key) {
        if (intent == null || TextUtils.isEmpty(key)) {
            return -1;
        }
        return intent.getInt(key, -1);
    }

    public static Serializable getSerializableExtra(Bundle intent, String key) {
        if (intent == null || TextUtils.isEmpty(key)) {
            return null;
        }
        return intent.getSerializable(key);
    }
}
