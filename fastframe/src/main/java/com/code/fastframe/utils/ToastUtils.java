package com.code.fastframe.utils;

import android.widget.Toast;

import com.code.fastframe.FastFrame;

/**
 * ToastUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-12-9
 */
public class ToastUtils {
    private static Toast toast = null; //Toast的对象！

    public static void showToast(String id) {
        if (toast == null) {
            toast = Toast.makeText(FastFrame.mApplicationContext, id, Toast.LENGTH_SHORT);
        }
        else {
            toast.setText(id);
        }
        toast.show();
    }
}
