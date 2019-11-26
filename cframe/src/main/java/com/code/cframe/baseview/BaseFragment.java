package com.code.cframe.baseview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.code.cframe.GlobalEvent;
import com.code.cframe.ciface.IBaseEvent;
import com.code.cframe.ciface.IBaseViewLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/12/6.
 */

public abstract class BaseFragment extends Fragment implements IBaseViewLayout, IBaseEvent {

    private View rootView;

    private List<String> eventList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }
        if (needGlobalEvent()) {
            EventBus.getDefault().register(this);
            setEvents();
        }
        int layoutId = this.setContentLayout();
        if (layoutId == 0) {
            throw new RuntimeException("setLayout first~~");
        } else {
            rootView = inflater.inflate(layoutId, null);
        }
        if (rootView != null) {
            this.initView();
            this.getBundleData();
            this.getNetData();
        }
        return rootView;
    }

    @Override
    public void initData() {

    }

    @Override
    public View findView(int id) {
        return rootView.findViewById(id);
    }

    public void getBundleData() {

    }

    public void getNetData() {
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GlobalEvent event) {
        /* Do something */
        for (String s : eventList) {
            if (s.equals(event.getMsgId())) {
                eventComming(event);
                break;
            }
        }
    }

    @Override
    public boolean needGlobalEvent() {
        return false;
    }

    @Override
    public List<String> regeistEvent() {
        return null;
    }

    private void setEvents() {
        List<String> events = regeistEvent();
        if (events != null) {
            eventList.addAll(events);
        }
    }
}
