package com.code.fastframe.utils;

import com.code.fastframe.FastFrame;
import com.code.fastframe.R;

public class SPUtils {

    private static int sp1;

    public static int get(int size) {
        if (sp1 == 0) {
            sp1 = getRes(R.dimen.sp1);
        }
        return (sp1 * size);
    }

    public static int getRes(int id) {
        try {
            return (int) FastFrame.mApplicationContext.getResources().getDimension(id);
        } catch (Exception e) {
            return 0;
        }
    }
}
