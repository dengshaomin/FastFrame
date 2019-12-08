package com.code.fastframe.utils;

import com.code.fastframe.FastFrame;

public class DPUtils {

    private static int dp1;

    public static int get(int size) {
        if (dp1 == 0) {
            dp1 = DensityUtil.dip2px(1);
        }
        return (dp1 * size);
    }

    public static int getRes(int id) {
        try {
            return (int) FastFrame.mApplicationContext.getResources().getDimension(id);
        } catch (Exception e) {
            return 0;
        }
    }
}
