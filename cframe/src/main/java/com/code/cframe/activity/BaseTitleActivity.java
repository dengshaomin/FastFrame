package com.code.cframe.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.code.cframe.R;
import com.code.cframe.ciface.IBaseLayout;
import com.code.cframe.ciface.IBaseViewLayout;
import com.code.cframe.ciface.ITitle;

/**
 * Created by dengshaomin on 2017/11/7.
 */

public abstract class BaseTitleActivity extends BaseBundleActivity implements IBaseLayout, ITitle {

    protected ViewGroup container;

    private View left_image, right_image;

    private TextView title_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_base);
        initTitle();
        this.initView();
        this.initData();
        this.getNetData();
    }
    public void getNetData() {
    }

    @Override
    public void initData() {

    }

    private void initTitle() {
        container = findViewById(R.id.container);
        if (setContentLayout() != 0) {
            LayoutInflater.from(this).inflate(setContentLayout(), container);
        }
        title_text = findViewById(R.id.title_text);
        left_image = findViewById(R.id.left_image);
        right_image = findViewById(R.id.right_image);
        left_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleLeftClick();
            }
        });
        right_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleRightClick();
            }
        });
        title_text.setText(TextUtils.isEmpty(setTitleText()) ? "" : setTitleText());
        if (setTitleLeftImage() > 0) {
            left_image.setBackgroundResource(setTitleLeftImage());
        }
        if (setTitleRightImage() > 0) {
            left_image.setBackgroundResource(setTitleRightImage());
        }
    }

    @Override
    public void titleLeftClick() {
        onBackPressed();
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
    public void titleRightClick() {

    }
}
