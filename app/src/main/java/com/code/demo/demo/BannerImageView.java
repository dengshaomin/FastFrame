package com.code.demo.demo;

import android.content.Context;
import android.util.AttributeSet;

import com.code.fastframe.baseview.BaseViewLayout;
import com.code.fastframe.utils.ImageUtils;
import com.code.demo.R;
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