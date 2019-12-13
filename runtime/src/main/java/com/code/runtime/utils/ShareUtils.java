package com.code.runtime.utils;

import java.io.Serializable;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;

import com.billy.cc.core.component.CC;
import com.code.runtime.contants.ComponentsContants;
import com.code.runtime.contants.ComponentsContants.Action;
import com.code.runtime.contants.ComponentsContants.Name;
import com.code.runtime.contants.ShareContants.Plat;
import com.code.runtime.dialog.FullDialog;
import com.code.runtime.dialog.ShareDialogView;
import com.code.runtime.dialog.ShareDialogView.Bean;
import com.code.runtime.dialog.ShareDialogView.IShareDialogView;

public class ShareUtils {

    public static void share(Context context, ShareBean shareBean) {
        if (shareBean == null || !(context instanceof FragmentActivity)) {
            return;
        }
        FullDialog fullDialog = FullDialog.newIntance(((FragmentActivity) context).getSupportFragmentManager());
        ShareDialogView shareDialogView = new ShareDialogView(context);
        shareDialogView.setIShareDialogView(new IShareDialogView() {
            @Override
            public void shareItemClick(Bean bean) {
                fullDialog.dismiss();
                if (bean == null) {
                    return;
                }
                String componentname = null;
                switch (bean.plat) {
                    case Plat.WECHART:
                        componentname = Name.Wechart;
                        break;
                    case Plat.QQ:
                    case Plat.QZONE:
                        componentname = Name.QQ;
                        break;
                }
                if (TextUtils.isEmpty(componentname)) {
                    return;
                }
                shareBean.plat = bean.plat;
                CC.obtainBuilder(componentname).setContext(context).setActionName(Action.Share).addParam(ComponentsContants.VIEW_DATA,
                        shareBean)
                        .build().call();
            }
        });

        fullDialog.setContentView(shareDialogView)
                .setGravity(Gravity.BOTTOM)
                .setNeedBackGround(true)
                .showDidlog();
    }

    public static class ShareBean implements Serializable {

        public int plat;

        public int type;

        public String title;

        public String content;

        public String imageUrl;

        public String clickUrl;
    }

    public static class Builder {

        private int plat;

        public int type;

        private String title;

        private String content;

        private String imageUrl;

        private String clickUrl;

        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public Builder setPlat(int plat) {
            this.plat = plat;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
            return this;
        }

        public ShareBean build() {
            ShareBean shareBean = new ShareBean();
            shareBean.plat = this.plat;
            shareBean.type = this.type;
            shareBean.clickUrl = this.clickUrl;
            shareBean.content = this.content;
            shareBean.imageUrl = this.imageUrl;
            shareBean.title = this.title;
            return shareBean;
        }
    }

    public static interface IShareResult {

        void shareResult(ShareReult shareReult);
    }

    public static class ShareReult {

        public int result;

        public int plat;

        public ShareReult(int result, int plat) {
            this.result = result;
            this.plat = plat;
        }
    }

}
