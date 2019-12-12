package com.code.qqcomponent;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.CCUtil;
import com.billy.cc.core.component.IComponent;
import com.code.qq_component.BuildConfig;
import com.code.runtime.contants.ComponentsContants;
import com.code.runtime.contants.ComponentsContants.Action;
import com.code.runtime.contants.ComponentsContants.Name;
import com.code.runtime.utils.ShareUtils.ShareBean;
import com.tencent.tauth.Tencent;

public class QQComponent implements IComponent {

    public static Tencent mTencent;

    public static final String Scope = "all";

    @Override
    public String getName() {
        //指定组件的名称
        return Name.QQ;
    }

    @Override
    public boolean onCall(CC cc) {
        initTencent();
        Intent intent = new Intent(cc.getContext(), QQTransitionActivity.class);
        intent.putExtra(ComponentsContants.ACTION_DATA, cc.getActionName());
        if (cc.getParams() != null) {
            intent.putExtra(ComponentsContants.VIEW_DATA, (Serializable) cc.getParams().get(ComponentsContants.VIEW_DATA));
        }
        cc.getContext().startActivity(intent);
        String actionName = cc.getActionName();
        if (TextUtils.equals(actionName, Action.Share)) {

        }
        CC.sendCCResult(cc.getCallId(), CCResult.success());
        //同步方式实现（在return之前听过CC.sendCCResult()返回组件调用结果），return false
        return false;
    }

    private void initTencent() {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(BuildConfig.QQ_APP_ID, CC.getApplication());
        }
    }
}
