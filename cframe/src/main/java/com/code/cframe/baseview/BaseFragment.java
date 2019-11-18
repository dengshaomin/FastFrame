package com.code.cframe.baseview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.code.cframe.GlobalMsg;
import com.code.cframe.ciface.IBaseLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/12/6.
 */

public abstract class BaseFragment extends Fragment implements IBaseLayout {
    private View rootView;
    private List<String> eventList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView != null) return rootView;
        EventBus.getDefault().register(this);
        setEvents();
        int layoutId = this.setContentLayout();
        if (layoutId == 0) {
            TextView tx = new TextView(getActivity());
            tx.setText("setLayout first~~");
            rootView = tx;
        } else {
            rootView = inflater.inflate(layoutId, null);
        }
        if (rootView != null) {
            this.initView();
            this.initBundleData();
            this.getNetData();
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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

    private void setEvents() {
        List<String> events = regeistEvent();
        if (events != null) {
            eventList.addAll(events);
        }
    }
}
