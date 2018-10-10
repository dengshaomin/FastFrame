package com.code.codefram;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;

import com.code.codeframlibrary.commons.GlobalMsg;
import com.code.codeframlibrary.commons.baseview.BaseLayout;
import com.code.codeframlibrary.commons.utils.CImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public class BannerImageView extends BaseLayout {


    SimpleDraweeView image;

    public BannerImageView(Context context) {
        super(context);
    }

    public BannerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int setContentLayout() {
        return R.layout.view_banner_image;
    }

    @Override
    public void initView() {
        image = (SimpleDraweeView) findView(R.id.image);
    }

    @Override
    public void initBundleData() {

    }

    @Override
    public void getNetData() {

    }

    @Override
    public List<String> regeistEvent() {
        return null;
    }

    @Override
    public void eventComming(GlobalMsg globalMsg) {

    }

    @Override
    public void setViewData(Object data) {
        CImageUtils.getInstance().loadImage(image, data + "");
    }
}
