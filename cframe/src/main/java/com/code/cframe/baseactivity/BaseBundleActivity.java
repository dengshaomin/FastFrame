package com.code.cframe.baseactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.code.cframe.ciface.IBaseBundle;

public abstract class BaseBundleActivity extends BaseEventActivity implements IBaseBundle {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBunleData();
    }

    @Override
    public void getBunleData() {

    }
}
