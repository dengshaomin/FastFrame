package com.code.wechartcomponent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.code.fastframe.ciface.IImageUtils;
import com.code.fastframe.eventbus.GlobalEvent;
import com.code.fastframe.utils.BitmapUtil;
import com.code.fastframe.utils.FileUtils;
import com.code.fastframe.utils.ImageUtils;
import com.code.fastframe.utils.IntentUtils;
import com.code.runtime.contants.ComponentsContants;
import com.code.runtime.contants.ComponentsContants.Action;
import com.code.runtime.contants.GlobalEventContants;
import com.code.runtime.contants.Result;
import com.code.runtime.contants.ShareContants.Plat;
import com.code.runtime.contants.ShareContants.Type;
import com.code.runtime.models.events.ThirdPlatEventBean;
import com.code.runtime.utils.ShareUtils.ShareBean;
import com.code.weixin_component.R;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import org.greenrobot.eventbus.EventBus;

import static com.code.wechartcomponent.WechartComponent.mIWXAPI;


public class WechartTransitionActivity extends AppCompatActivity {

    private String action = null;

    private ShareBean mShareBean;

    private static final int THUMB_SIZE = 150;

    private Bitmap sourceBitmap = null, thumbBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqtransition);
        action = IntentUtils.getStringExtra(getIntent(), ComponentsContants.ACTION_DATA);
        mShareBean = (ShareBean) IntentUtils.getSerializableExtra(getIntent(), ComponentsContants.VIEW_DATA);
        if (TextUtils.equals(action, Action.Login)) {
            login();
        } else if (TextUtils.equals(action, Action.Share)) {
            share();
        }
    }

    private void share() {
        if (mShareBean.type == Type.IMAGE) {
            shareImage();
        } else if (mShareBean.type == Type.IMAGE_TEXT) {
            shareUrl();
        }
    }

    private void shareImage() {
        if (FileUtils.isFileExist(mShareBean.imageUrl)) {
            sourceBitmap = BitmapFactory.decodeFile(mShareBean.imageUrl);
            shareImageReally();
        } else {
            ImageUtils.getInstance().downLoadImage(mShareBean.imageUrl, new IImageUtils() {
                @Override
                public void downloadFinish(Bitmap bitmap) {
                    sourceBitmap = bitmap;
                    shareImageReally();
                }
            });
        }
    }

    private void shareImageReally() {
        if (sourceBitmap == null) {
            notifyResult(Result.FAIL, "图片处理失败");
            return;
        }
        WXImageObject imgObj = new WXImageObject(sourceBitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        thumbBitmap = Bitmap.createScaledBitmap(sourceBitmap, THUMB_SIZE, THUMB_SIZE, true);
        releaseBitmap(sourceBitmap);
        if (thumbBitmap == null) {
            notifyResult(Result.FAIL, "图片处理失败");
            return;
        }
        msg.thumbData = BitmapUtil.getBytesFromBitmap(thumbBitmap);
        releaseBitmap(thumbBitmap);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = mShareBean.plat == Plat.WECHART ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        mIWXAPI.sendReq(req);
        finish();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void shareUrl() {
        if (FileUtils.isFileExist(mShareBean.imageUrl)) {
            sourceBitmap = BitmapFactory.decodeFile(mShareBean.imageUrl);
            shareUrlReally();
        } else {
            ImageUtils.getInstance().downLoadImage(mShareBean.imageUrl, new IImageUtils() {
                @Override
                public void downloadFinish(Bitmap bitmap) {
                    sourceBitmap = bitmap;
                    shareUrlReally();
                }
            });
        }
    }

    private void shareUrlReally() {
        if (sourceBitmap == null) {
            notifyResult(Result.FAIL, "图片处理失败");
            return;
        }
        //初始化一个WXWebpageObject，填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = mShareBean.clickUrl;

        //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        thumbBitmap = Bitmap.createScaledBitmap(sourceBitmap, THUMB_SIZE, THUMB_SIZE, true);
//        releaseBitmap(sourceBitmap);
        if (thumbBitmap == null) {
            notifyResult(Result.FAIL, "图片处理失败");
            return;
        }
        msg.thumbData = BitmapUtil.getBytesFromBitmap(thumbBitmap);
        releaseBitmap(thumbBitmap);
        msg.title = mShareBean.title;
        msg.description = mShareBean.content;
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = mShareBean.plat == Plat.WECHART ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
//        req.userOpenId = getOpenId();
        //调用api接口，发送数据到微信
        mIWXAPI.sendReq(req);
        finish();
    }

    private void login() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo,snsapi_friend,snsapi_message,snsapi_contact";
        req.state = "none";
        mIWXAPI.sendReq(req);
        finish();
    }

    private void notifyResult(int result, String msg) {
        if (TextUtils.equals(Action.Share, action)) {
            EventBus.getDefault().post(new GlobalEvent(GlobalEventContants.THIRD_PLAT, new ThirdPlatEventBean(Plat.WECHART, action, result, msg)));
        } else if (TextUtils.equals(Action.Login, action)) {
            EventBus.getDefault().post(new GlobalEvent(GlobalEventContants.THIRD_PLAT, new ThirdPlatEventBean(Plat.WECHART, action, result, msg)));
        }
        finish();
    }

    private void releaseBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}
