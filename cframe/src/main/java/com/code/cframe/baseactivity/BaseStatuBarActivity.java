package com.code.cframe.baseactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.code.cframe.R;
import com.code.cframe.ciface.IBaseStatuBarActivity;
import com.code.cframe.utils.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseStatuBarActivity extends RxAppCompatActivity implements IBaseStatuBarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(needNavigationBarTransparent()){
//            StatusBarUtil.setTransparent(this);
        }
        if(needStatuBarTransparent()){
            StatusBarUtil.setColor(this,getResources().getColor(R.color.titleBarColor),0);
        }
    }

    @Override
    public boolean needStatuBarTransparent() {
        return false;
    }

    @Override
    public boolean needNavigationBarTransparent() {
        return false;
    }
}
