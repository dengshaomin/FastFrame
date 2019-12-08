package com.code.demo.xinge;

import android.content.Context;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

public class XinGeReceiver extends XGPushBaseReceiver {

    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {
        int a = 1;
    }

    @Override
    public void onUnregisterResult(Context context, int i) {
        int a = 1;
    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {
        int a = 1;
    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {
        int a = 1;
    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
        int a = 1;
    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {
        int a = 1;
    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {
        int a = 1;
    }
}
