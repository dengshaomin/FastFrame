package com.code.cframe.baseview;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.code.cframe.R;
import com.code.cframe.GlobalMsg;
import com.code.cframe.ciface.IBaseLayout;
import com.code.cframe.ciface.IBasePresent;
import com.code.cframe.ciface.ITitle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by dengshaomin on 2017/11/7.
 */

public abstract class BaseTitleActivity extends PermissionActivity implements IBaseLayout, ITitle {

    LinearLayout container;

    View left_image, right_image;

    TextView title_text;


    private List<String> eventList = new ArrayList<>();


    protected IBasePresent mIBasePresents;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_title_base);
        initTitle();
        List<String> events = regeistEvent();
        if (events != null) {
            eventList.addAll(events);
        }
        this.initView();
        this.initBundleData();
        mIBasePresents = getPresents();
        this.getNetData();
    }


    private void initTitle() {
        container = findViewById(R.id.container);
        if (setContentLayout() != 0) {
            LayoutInflater.from(this).inflate(setContentLayout(), container);
        }
        findViewById(R.id.title_lay).setVisibility(needTitle() ? View.VISIBLE : View.GONE);
        if (needTitle()) {
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
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        releasePresents();
        super.onDestroy();
    }

    void releasePresents() {
        if (mIBasePresents != null) {
            mIBasePresents.destory();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GlobalMsg event) {
        /* Do something */
        for (String s : eventList) {
            if (s.equals(event.getMsgId())) {
                eventComming(event);
                break;
            }
        }
    }

    public Object getBunleData() {
        if (getIntent() != null) {
            return getIntent().getSerializableExtra(this.getClass().getName());
        }
        return null;
    }

    public abstract IBasePresent getPresents();

    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
//            mWebView.goBack();// 返回前一个页面
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
        return super.onKeyDown(keyCode, event);
    }

}
