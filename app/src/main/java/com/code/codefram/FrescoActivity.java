package com.code.codefram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.code.codeframlibrary.commons.utils.CImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FrescoActivity extends AppCompatActivity {

    @BindView(R.id.image)
    SimpleDraweeView mImage;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);
        mUnbinder = ButterKnife.bind(this);
        CImageUtils.getInstance().loadImage(mImage, "http://pic4.nipic.com/20091217/3885730_124701000519_2.jpg");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}
