package com.code.wechartcomponent;

import android.content.Context;
import android.text.TextUtils;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.code.runtime.contants.ComponentsContants.Action;
import com.code.runtime.contants.ComponentsContants.Name;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WechartComponent implements IComponent {

    private IWXAPI mIWXAPI;
    private static final  String APP_ID = "";
    @Override
    public String getName() {
        //指定组件的名称
        return Name.Wechart;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        if (TextUtils.equals(actionName, Action.Share)) {

        }
        CC.sendCCResult(cc.getCallId(), CCResult.success());
        //同步方式实现（在return之前听过CC.sendCCResult()返回组件调用结果），return false
        return false;
    }

    private void initWxApi(){
        if(mIWXAPI == null){
//            mIWXAPI  = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);;
        }
    }
}
