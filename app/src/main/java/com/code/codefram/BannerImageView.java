package com.code.codefram;

import android.content.Context;
import android.util.AttributeSet;

import com.code.cframe.baseview.BaseViewLayout;
import com.code.cframe.utils.ImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public class BannerImageView extends BaseViewLayout {


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
    public void setViewData(Object data) {
        ImageUtils.getInstance().loadImage(image, data + "");
    }
}
