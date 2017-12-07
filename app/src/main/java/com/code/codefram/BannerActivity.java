package com.code.codefram;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.ImageView;

import com.code.codeframlibrary.commons.GlobalMsg;
import com.code.codeframlibrary.commons.baseview.BaseTitleActivity;
import com.code.codeframlibrary.commons.utils.CImageUtils;
import com.github.lazylibrary.util.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import butterknife.BindView;

public class BannerActivity extends BaseTitleActivity implements OnBannerListener {


    List<String> imageUrl = new ArrayList<String>() {{
        add("http://pic6.nipic.com/20091207/3337900_161732052452_2.jpg");
        add("http://img.juimg.com/tuku/yulantu/120926/219049-12092612022378.jpg");
        add("http://pic.58pic.com/58pic/13/70/90/29358PICQjG_1024.jpg");
    }};

    @BindView(R.id.banner)
    Banner mBanner;

    @Override
    public boolean needTitle() {
        return true;
    }

    @Override
    public int setTitleLeftImage() {
        return 0;
    }

    @Override
    public int setTitleRightImage() {
        return 0;
    }

    @Override
    public String setTitleText() {
        return "Banner";
    }

    @Override
    public void titleRightClick() {

    }

    @Override
    public List<String> needPermissions() {
        return null;
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_banner;
    }

    @Override
    public void initView() {
        mBanner.setImages(imageUrl).setImageLoader(new FrescoImageLoader())
                .isAutoPlay(true)
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setOnBannerListener(this)
                .start();
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

    }


    @Override
    public void OnBannerClick(int position) {
        ToastUtils.showToast(this, position + "");
    }

    public class FrescoImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            CImageUtils.getInstance().loadImage(imageView, path + "");
        }
        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            ImageView simpleDraweeView = new ImageView(context);
            return simpleDraweeView;
        }
    }

}
