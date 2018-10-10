package com.code.codeframlibrary.commons.baseview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.code.codeframlibrary.commons.GlobalMsg;
import com.code.codeframlibrary.commons.ciface.IBaseLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dengshaomin on 2016/10/21.
 */
//如果是recycleview 的item继承此类，可能由于回收服用问题导致 unbinder被调用，找不到内部布局变量，此时将 ButterKnife.bind 转移到setviewdata
public abstract class BaseLayout extends LinearLayout implements IBaseLayout {

    private View rootView;

    private List<String> eventList = new ArrayList<>();

    private Unbinder unbinder;

    @Override
    public View getRootView() {
        return rootView;
    }

    public Context getmContext() {
        return mContext;
    }

    private Context mContext;

    public BaseLayout(Context context) {
        this(context, null);
    }

    public BaseLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public BaseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        EventBus.getDefault().register(this);
        setEvents();
        this.mContext = context;
        int layoutId = this.setContentLayout();
        if (layoutId == 0) {
            TextView tx = new TextView(mContext);
            tx.setText("setLayout first~~");
            this.addView(tx);
            return;
        }
        rootView = LayoutInflater.from(mContext).inflate(layoutId, this);
        unbinder = ButterKnife.bind(this, rootView);
        if (rootView != null) {
            this.initView();
            this.initBundleData();
            this.getNetData();
        }
    }

    public View findView(int id) {
        return rootView.findViewById(id);
    }

    private void setEvents() {
        List<String> events = regeistEvent();
        if (events != null) {
            eventList.addAll(events);
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        //unrefeist
//        EventBus.getDefault().unregister(this);
//        try {
//            if (unbinder != null) {
//                unbinder.unbind();
//            }
//        } catch (Exception e) {
//
//        }
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

    public void dialogDismiss() {

    }


    public void setPingBackParams(String rpage, String rblock) {

    }

    public void setPingBackParams(String rpage, String rblock, int index) {

    }
}
