package com.code.runtime.utils;

import android.content.Context;
import android.text.TextUtils;

import com.billy.cc.core.component.CC;
import com.code.runtime.contants.ComponentsContants.Action;
import com.code.runtime.contants.ComponentsContants.Name;
import com.code.runtime.contants.ShareCotants.Plat;

public class LoginUtils {

    public static void login(Context context, int plat) {
        String componentname = "";
        switch (plat) {
            case Plat.WECHART:
                componentname = Name.Wechart;
                break;
            case Plat.QQ:
                componentname = Name.QQ;
                break;
        }
        if (TextUtils.isEmpty(componentname)) {
            return;
        }
        CC.obtainBuilder(componentname).setContext(context).setActionName(Action.Login)
                .build().call();
    }
}
