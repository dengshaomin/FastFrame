package com.code.cframe.utils;

import java.io.File;

import android.os.Environment;
import android.text.TextUtils;

import com.github.lazylibrary.util.FileUtils;

/**
 * Created by dengshaomin on 2017/12/6.
 */

public class CFlieUtils {

    public static void saveStrToFile(String name, String value) {
        if (TextUtils.isEmpty(name)) {
            return;
        }
        FileUtils.saveStrToFile(value, Environment.getExternalStorageDirectory() + File.separator + name);
    }

    public static String readFile(String path) {
        StringBuilder stringBuilder = FileUtils.readFile(Environment.getExternalStorageDirectory() + File.separator + path, "UTF-8");
        return stringBuilder == null ? null : stringBuilder.toString();
    }
}
