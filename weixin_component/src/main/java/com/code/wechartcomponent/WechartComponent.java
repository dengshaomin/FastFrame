package com.code.wechartcomponent;

import java.io.Serializable;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.code.runtime.contants.ComponentsContants;
import com.code.runtime.contants.ComponentsContants.Action;
import com.code.runtime.contants.ComponentsContants.Name;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WechartComponent implements IComponent {

    public static IWXAPI mIWXAPI;

    private static final String APP_ID = "wx42d00fdcc65e9f48";

    @Override
    public String getName() {
        //指定组件的名称
        return Name.Wechart;
    }

    @Override
    public boolean onCall(CC cc) {
        initWxApi(cc.getContext());
        Intent intent = new Intent(cc.getContext(), WechartTransitionActivity.class);
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


    public static synchronized void initWxApi(Context context) {
        if (mIWXAPI == null) {
            mIWXAPI = WXAPIFactory.createWXAPI(context.getApplicationContext() == null ? context : context.getApplicationContext(), APP_ID, false);
            mIWXAPI.registerApp(APP_ID);
        }
    }

}
