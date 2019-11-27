package com.code.cframe.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

public class CompoundDrawableUtils {

    public static  void setDrawalbes(Context context, TextView textView, int resId, int gravity) {
        if (textView == null || context == null || resId == 0) {
            return;
        }
        Drawable drawable = context.getResources().getDrawable(resId);
//        textView.setCompoundDrawablePadding(5);
        textView.setCompoundDrawablesWithIntrinsicBounds(gravity == Gravity.LEFT ? drawable : null, gravity == Gravity.TOP ? drawable : null,
                gravity == Gravity.RIGHT ? drawable : null, gravity == Gravity.BOTTOM ? drawable : null);
    }

    public static void setDrawalbes(Context context, TextView textView, int resId, int gravity,int padding) {
        if (textView == null || context == null || resId == 0) {
            return;
        }
        Drawable drawable = context.getResources().getDrawable(resId);
        textView.setCompoundDrawablePadding(padding);
        textView.setCompoundDrawablesWithIntrinsicBounds(gravity == Gravity.LEFT ? drawable : null, gravity == Gravity.TOP ? drawable : null,
                gravity == Gravity.RIGHT ? drawable : null, gravity == Gravity.BOTTOM ? drawable : null);
    }
}
