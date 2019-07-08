package com.code.codefram;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.code.codeframlibrary.commons.utils.CImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;


public class FrescoActivity extends AppCompatActivity {

    SimpleDraweeView mImage;


    AnimationDrawable mAnimationDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);
        mImage = findViewById(R.id.image);
        CImageUtils.getInstance().loadImage(mImage, "http://pic4.nipic.com/20091217/3885730_124701000519_2.jpg");

        ImageView text = findViewById(R.id.text);
        text.setBackgroundDrawable(getResources().getDrawable(R.drawable.animation_list));
        mAnimationDrawable = (AnimationDrawable) text.getBackground();
        mAnimationDrawable.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
