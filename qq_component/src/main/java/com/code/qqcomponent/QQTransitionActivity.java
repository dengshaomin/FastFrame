package com.code.qqcomponent;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.hsf.HSFJSONUtils;
import com.code.fastframe.eventbus.GlobalEvent;
import com.code.fastframe.utils.AppUtils;
import com.code.fastframe.utils.IntentUtils;
import com.code.runtime.contants.ComponentsContants;
import com.code.runtime.contants.ComponentsContants.Action;
import com.code.runtime.contants.GlobalEventContants;
import com.code.runtime.contants.Result;
import com.code.runtime.contants.ShareContants.Plat;
import com.code.runtime.contants.ShareContants.Type;
import com.code.runtime.factory.UserInfoFactory;
import com.code.runtime.models.events.ThirdPlatEventBean;
import com.code.runtime.models.qq.QQLoginBean;
import com.code.runtime.models.qq.QQUserInfoBean;
import com.code.runtime.utils.LoginUtils;
import com.code.runtime.utils.ShareUtils.ShareBean;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import static com.code.qqcomponent.QQComponent.Scope;
import static com.code.qqcomponent.QQComponent.mTencent;

public class QQTransitionActivity extends AppCompatActivity {

    private String action = null;

    private ShareBean mShareBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        action = IntentUtils.getStringExtra(getIntent(), ComponentsContants.ACTION_DATA);
        mShareBean = (ShareBean) IntentUtils.getSerializableExtra(getIntent(), ComponentsContants.VIEW_DATA);
        if (TextUtils.equals(action, Action.Login)) {
            if (!mTencent.isSessionValid()) {
                mTencent.login(this, Scope, mLoginQQUiListener);
            }
        } else if (TextUtils.equals(action, Action.Share)) {
            if (mShareBean == null) {
                notifyResult(Result.FAIL, "sharebean is null");
                return;
            }
            if (mShareBean.type == Type.IMAGE) {
                Bundle params = new Bundle();
                //本地图片地址
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, mShareBean.imageUrl);
                //手Q客户端顶部替换返回按钮
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, AppUtils.getPackageName(this));
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
//                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                mTencent.shareToQQ(this, params, mShareQQUiListener);
            } else if (mShareBean.type == Type.IMAGE_TEXT) {
                final Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, mShareBean.title);
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, mShareBean.content);
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mShareBean.clickUrl);
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, mShareBean.imageUrl);
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, AppUtils.getPackageName(this));
//                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
                mTencent.shareToQQ(this, params, mShareQQUiListener);
            }
        }
    }

    private QQUiListener mShareQQUiListener = new QQUiListener() {
        @Override
        public void onComplete(Object o) {
            if (o == null) {
                notifyResult(Result.FAIL, null);
                return;
            }
            JSONObject jsonObject = (JSONObject) o;
            if (jsonObject == null) {
                notifyResult(Result.FAIL, null);
                return;
            }
            int ret = -1;
            try {
                ret = jsonObject.getInt("ret");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (ret == -1) {
                notifyResult(Result.FAIL, null);
                return;
            }
            notifyResult(Result.SUCCESS, null);
        }

        @Override
        public void onError(UiError uiError) {
            notifyResult(Result.FAIL, null);
        }

        @Override
        public void onCancel() {
            notifyResult(Result.CALCEL, null);
        }
    };

    private QQUiListener mLoginQQUiListener = new QQUiListener() {
        @Override
        public void onComplete(Object o) {
            if (o == null) {
                notifyResult(Result.FAIL, null);
                return;
            }
            JSONObject jsonObject = (JSONObject) o;
            if (jsonObject == null) {
                notifyResult(Result.FAIL, null);
                return;
            }
            int ret = -1;
            try {
                ret = jsonObject.getInt("ret");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (ret == -1) {
                notifyResult(Result.FAIL, null);
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
                        notifyResult(Result.FAIL, null);
                        return;
                    }
                    JSONObject jsonObject = (JSONObject) o;
                    if (jsonObject == null) {
                        notifyResult(Result.FAIL, null);
                        return;
                    }
                    int ret = -1;
                    try {
                        ret = jsonObject.getInt("ret");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (ret == -1) {
                        notifyResult(Result.FAIL, null);
                        return;
                    }
                    UserInfoFactory.getInstance().mQQUserInfoBean = JSON.parseObject(o.toString(), QQUserInfoBean.class);
                    notifyResult(Result.SUCCESS, o.toString());
                }

                @Override
                public void onError(UiError uiError) {
                    notifyResult(Result.FAIL, null);
                }

                @Override
                public void onCancel() {
                    notifyResult(Result.CALCEL, null);
                }
            });
        }

        @Override
        public void onError(UiError uiError) {
            notifyResult(Result.FAIL, null);
        }

        @Override
        public void onCancel() {
            notifyResult(Result.CALCEL, null);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        mTencent.onActivityResultData(requestCode, resultCode, data, mLoginQQUiListener);
//        if (requestCode == Constants.REQUEST_API) {
//            if (resultCode == Constants.REQUEST_QQ_SHARE ||
//                    resultCode == Constants.REQUEST_QZONE_SHARE ||
//                    resultCode == Constants.REQUEST_OLD_SHARE) {
//                Tencent.handleResultData(data, mShareQQUiListener);
//            }
//        }

    }

    private void notifyResult(int result, String msg) {
        if (TextUtils.equals(Action.Share, action)) {
            EventBus.getDefault().post(new GlobalEvent(GlobalEventContants.THIRD_PLAT, new ThirdPlatEventBean(Plat.QQ, action, result, msg)));
        } else if (TextUtils.equals(Action.Login, action)) {
            EventBus.getDefault().post(new GlobalEvent(GlobalEventContants.THIRD_PLAT, new ThirdPlatEventBean(Plat.QQ, action, result, msg)));
        }
        finish();
    }
}
