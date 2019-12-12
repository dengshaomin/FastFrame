package com.code.weixin_component;

import android.text.TextUtils;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.code.runtime.contants.ComponentsContants.Action;
import com.code.runtime.contants.ComponentsContants.Name;

public class WechartComponent implements IComponent {

    @Override
    public String getName() {
        //指定组件的名称
        return Name.Wechart;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        if (TextUtils.equals(actionName, Action.Share)) {
            int a = 1;
        }
        CC.sendCCResult(cc.getCallId(), CCResult.success());
        return false;
    }
}
