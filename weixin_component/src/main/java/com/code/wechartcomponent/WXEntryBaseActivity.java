package com.code.wechartcomponent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.andview.refreshview.utils.LogUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryBaseActivity extends Activity implements IWXAPIEventHandler {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Intent intent = getIntent();
            WechartComponent.mIWXAPI.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WechartComponent.mIWXAPI.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        LogUtils.e(JSON.toJSONString(req));
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        LogUtils.e(JSON.toJSONString(resp));
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK: //发送成功
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://发送取消

                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://发送被拒绝
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT://不支持错误
                break;
            default://发送返回

                break;
        }
        finish();
    }
}
