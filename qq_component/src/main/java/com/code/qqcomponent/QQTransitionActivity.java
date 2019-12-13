package com.code.qqcomponent;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.code.fastframe.eventbus.GlobalEvent;
import com.code.fastframe.utils.AppUtils;
import com.code.fastframe.utils.IntentUtils;
import com.code.fastframe.utils.StroageUtils;
import com.code.qq_component.R;
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
import com.code.runtime.utils.ShareUtils.ShareBean;
import com.tencent.connect.UserInfo;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.code.qqcomponent.QQComponent.Scope;
import static com.code.qqcomponent.QQComponent.mTencent;
import static com.tencent.connect.share.QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD;

public class QQTransitionActivity extends AppCompatActivity {

    private String action = null;

    private ShareBean mShareBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqtransition);
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
                if (!isLocalImage(mShareBean.imageUrl)) {
                    notifyResult(Result.FAIL, "只支持传入本地图片地址");
                    return;
                }
                File file = new File(mShareBean.imageUrl);
                if (file == null || !file.exists()) {
                    if (!isLocalImage(mShareBean.imageUrl)) {
                        notifyResult(Result.FAIL, "图片资源获取失败");
                        return;
                    }
                }
                if (file.length() >= 5 * 1024 * 1024) {
                    //qq控件只支持分享5M以下图片
                    Luban.with(this)
                            .load(mShareBean.imageUrl)
                            .ignoreBy(4 * 1024)//单位k
                            .setTargetDir(StroageUtils.getProtectPath().getPath())
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                }

                                @Override
                                public void onSuccess(File file) {
                                    // TODO 压缩成功后调用，返回压缩后的图片文件
                                    if (file == null || !file.exists()) {
                                        notifyResult(Result.FAIL, "图片压缩失败");
                                        return;
                                    }
                                    mShareBean.imageUrl = file.getAbsolutePath();
                                    if (mShareBean.plat == Plat.QQ) {
                                        qqShareImage();
                                    }else{
                                        qzoneShareImage();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                    notifyResult(Result.FAIL, "图片压缩失败");
                                }
                            }).launch();
                } else {
                    if (mShareBean.plat == Plat.QQ) {
                        qqShareImage();
                    }else{
                        qzoneShareImage();
                    }
                }

            } else if (mShareBean.type == Type.IMAGE_TEXT) {
                if (mShareBean.plat == Plat.QQ) {
                    qqShareImageText();
                } else {
                    qzoneShareImageText();
                }
            }
        }
    }

    private void qzoneShareImageText() {
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, mShareBean.title);//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, mShareBean.content);//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, mShareBean.clickUrl);//必填
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, new ArrayList<String>() {{
            add(mShareBean.imageUrl);
        }});
        mTencent.shareToQzone(this, params, mShareQQUiListener);
    }

    private void qzoneShareImage() {
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, new ArrayList<String>() {{
            add(mShareBean.imageUrl);
        }});
        params.putString(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH, "本地视频地址");
        Bundle extParams = new Bundle();
//        extParams.putString(QzonePublish.HULIAN_EXTRA_SCENE, "分享场景”);
//                extParams.putString(QzonePublish.HULIAN_CALL_BACK, "回调信息”);
//                        params.putBundle(QzonePublish.PUBLISH_TO_QZONE_EXTMAP, extParams);
        mTencent.publishToQzone(this, params, mShareQQUiListener);
    }

    private void qqShareImageText() {
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

    private void qqShareImage() {
        Bundle params = new Bundle();
        //本地图片地址
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, mShareBean.imageUrl);
        //手Q客户端顶部替换返回按钮
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, AppUtils.getPackageName(this));
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
//                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        mTencent.shareToQQ(this, params, mShareQQUiListener);
    }

    private boolean isLocalImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        return !path.startsWith("http");
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
