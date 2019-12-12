package com.code.qqcomponent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.code.fastframe.eventbus.GlobalEvent;
import com.code.fastframe.utils.IntentUtils;
import com.code.runtime.contants.ComponentsContants;
import com.code.runtime.contants.ComponentsContants.Action;
import com.code.runtime.contants.GlobalEventContants;
import com.code.runtime.contants.Result;
import com.code.runtime.contants.ShareCotants.Plat;
import com.code.runtime.factory.UserInfoFactory;
import com.code.runtime.models.events.ThirdPlatEventBean;
import com.code.runtime.models.qq.QQLoginBean;
import com.code.runtime.models.qq.QQUserInfoBean;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import static com.code.qqcomponent.QQComponent.Scope;
import static com.code.qqcomponent.QQComponent.mTencent;

public class QQTransitionActivity extends AppCompatActivity {

    private String action = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        action = IntentUtils.getStringExtra(getIntent(), ComponentsContants.ACTION_DATA);
        if (TextUtils.equals(action, Action.Login)) {
            if (!mTencent.isSessionValid()) {
                mTencent.login(this, Scope, mQQUiListener);
            }
        }
    }

    private QQUiListener mQQUiListener = new QQUiListener() {
        @Override
        public void onComplete(Object o) {
            if (o == null) {
                notifyResult(Result.FAIL);
                return;
            }
            JSONObject jsonObject = (JSONObject) o;
            if (jsonObject == null) {
                notifyResult(Result.FAIL);
                return;
            }
            int ret = -1;
            try {
                ret = jsonObject.getInt("ret");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (ret == -1) {
                notifyResult(Result.FAIL);
                return;
            }
            UserInfoFactory.getInstance().mQQLoginBean = JSON.parseObject(o.toString(), QQLoginBean.class);
            mTencent.setAccessToken(UserInfoFactory.getInstance().mQQLoginBean.getAccess_token(),
                    UserInfoFactory.getInstance().mQQLoginBean.getExpires_in());
            mTencent.setOpenId(UserInfoFactory.getInstance().mQQLoginBean.getOpenid());
            UserInfo userInfo = new UserInfo(QQTransitionActivity.this, mTencent.getQQToken());
            userInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    if (o == null) {
                        notifyResult(Result.FAIL);
                        return;
                    }
                    JSONObject jsonObject = (JSONObject) o;
                    if (jsonObject == null) {
                        notifyResult(Result.FAIL);
                        return;
                    }
                    int ret = -1;
                    try {
                        ret = jsonObject.getInt("ret");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (ret == -1) {
                        notifyResult(Result.FAIL);
                        return;
                    }
                    UserInfoFactory.getInstance().mQQUserInfoBean = JSON.parseObject(o.toString(), QQUserInfoBean.class);
                    notifyResult(Result.SUCCESS);
                }

                @Override
                public void onError(UiError uiError) {
                    notifyResult(Result.FAIL);
                }

                @Override
                public void onCancel() {
                    notifyResult(Result.CALCEL);
                }
            });
        }

        @Override
        public void onError(UiError uiError) {
            notifyResult(Result.FAIL);
        }

        @Override
        public void onCancel() {
            notifyResult(Result.CALCEL);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        mTencent.onActivityResultData(requestCode, resultCode, data, mQQUiListener);
//        finish();
    }

    private void notifyResult(int result) {
        EventBus.getDefault().post(new GlobalEvent(GlobalEventContants.THIRD_PLAT, new ThirdPlatEventBean(Plat.QQ, action, result)));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
