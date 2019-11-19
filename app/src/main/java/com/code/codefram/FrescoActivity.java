package com.code.codefram;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.code.cframe.utils.ImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;


public class FrescoActivity extends AppCompatActivity {

    SimpleDraweeView mImage;


    AnimationDrawable mAnimationDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);
        mImage = findViewById(R.id.image);
        ImageUtils.getInstance().loadImage(mImage, "http://m.iqiyipic.com/u5/image/20191113/07/92/pv_9308017100_d_601_700_350.jpg");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
