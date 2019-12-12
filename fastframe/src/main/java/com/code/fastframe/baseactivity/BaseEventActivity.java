package com.code.fastframe.baseactivity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.code.fastframe.eventbus.GlobalEvent;
import com.code.fastframe.ciface.IBaseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseEventActivity extends BasePermissionActivity implements IBaseEvent {

    private List<Integer> eventList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (needGlobalEvent()) {
            EventBus.getDefault().register(this);
            List<Integer> events = regeistEvent();
            if (events != null) {
                eventList.addAll(events);
            }
        }
    }

    @Override
    public List<Integer> regeistEvent() {
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
        for (Integer s : eventList) {
            if (s == event.getMsgId()) {
                eventComming(event);
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
