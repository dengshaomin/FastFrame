package com.code.demo.demo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.code.fastframe.eventbus.GlobalEvent;
import com.code.fastframe.baseactivity.BaseTitleActivity;
import com.code.fastframe.utils.ToastUtils;
import com.code.demo.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;


public class BannerActivity extends BaseTitleActivity implements OnBannerListener {


    List<String> imageUrl = new ArrayList<String>() {{
        add("http://pic6.nipic.com/20091207/3337900_161732052452_2.jpg");
        add("http://img.juimg.com/tuku/yulantu/120926/219049-12092612022378.jpg");
        add("http://pic.58pic.com/58pic/13/70/90/29358PICQjG_1024.jpg");
    }};

    Banner mBanner;

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
        mBanner = findViewById(R.id.banner);
        mBanner.setImages(imageUrl).setImageLoader(new FrescoImageLoader())
                .isAutoPlay(true)
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setOnBannerListener(this)
                .start();
    }

    @Override
    public void getNetData() {

    }


    @Override
    public void eventComming(GlobalEvent globalMsg) {

    }


    @Override
    public void OnBannerClick(int position) {
        ToastUtils.showToast( position + "");
    }


    public class FrescoImageLoader extends BannerImageLoader {

        @Override
        public void displayImage(Context context, Object path, BannerImageView imageView) {
            imageView.setViewData(path);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public BannerImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            return new BannerImageView(context);
        }
    }

    public abstract class BannerImageLoader implements ImageLoaderInterface<BannerImageView> {

        @Override
        public BannerImageView createImageView(Context context) {
            BannerImageView imageView = new BannerImageView(context);
            return imageView;
        }

    }
}
