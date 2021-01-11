package com.code.fastframe.baseview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.code.fastframe.ciface.IBaseEvent;
import com.code.fastframe.ciface.IBaseViewLayout;
import com.code.fastframe.eventbus.GlobalEvent;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/12/6.
 */

public abstract class BaseFragment extends Fragment implements IBaseViewLayout, IBaseEvent {

  private View rootView;

  private List<Integer> eventList = new ArrayList<>();

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
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

  public void getBundleData() {

  }

  public void getNetData() {
  }

  @Override
  public void setViewData(Object data) {

  }

  @Override
  public void eventComming(GlobalEvent globalEvent) {

  }

  @Override
  public void onDestroy() {
    EventBus.getDefault().unregister(this);
    super.onDestroy();
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
  public boolean needGlobalEvent() {
    return false;
  }

  @Override
  public List<Integer> regeistEvent() {
    return null;
  }

  private void setEvents() {
    List<Integer> events = regeistEvent();
    if (events != null) {
      eventList.addAll(events);
    }
  }
}
