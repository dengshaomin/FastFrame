package com.code.cframe.utils;

import android.content.Context;
import android.widget.Toast;

import com.code.cframe.CFrame;

/**
 * ToastUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-12-9
 */
public class ToastUtils {
    private static Toast toast = null; //Toast的对象！

    public static void showToast(String id) {
        if (toast == null) {
            toast = Toast.makeText(CFrame.mApplicationContext, id, Toast.LENGTH_SHORT);
        }
        else {
            toast.setText(id);
        }
        toast.show();
    }
}
