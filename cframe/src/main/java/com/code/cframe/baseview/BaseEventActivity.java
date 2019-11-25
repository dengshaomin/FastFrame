package com.code.cframe.baseview;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.code.cframe.GlobalEvent;
import com.code.cframe.ciface.IBaseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseEventActivity extends BasePermissionActivity implements IBaseEvent {

    private List<String> eventList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (needGlobalEvent()) {
            EventBus.getDefault().register(this);
            List<String> events = regeistEvent();
            if (events != null) {
                eventList.addAll(events);
            }
        }
    }

    @Override
    public List<String> regeistEvent() {
        return null;
    }

    @Override
    public void eventComming(GlobalEvent globalMsg) {

    }

    @Override
    public boolean needGlobalEvent() {
        return false;
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
    protected void onDestroy() {
        if (needGlobalEvent()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}
