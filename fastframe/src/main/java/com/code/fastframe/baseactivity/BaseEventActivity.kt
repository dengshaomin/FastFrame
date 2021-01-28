package com.code.fastframe.baseactivity

import android.os.Bundle
import com.code.fastframe.ciface.IBaseEvent
import com.code.fastframe.eventbus.GlobalEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode.MAIN
import java.util.ArrayList

abstract class BaseEventActivity : BasePermissionActivity(), IBaseEvent {
  private val eventList = mutableListOf<Int>()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (needGlobalEvent()) {
      EventBus.getDefault().register(this)
      val events = regeistEvent()
      if (events != null) {
        eventList.addAll(events)
      }
    }
  }

  override fun regeistEvent(): MutableList<Int> {
    return mutableListOf()
  }

  override fun eventComming(globalMsg: GlobalEvent) {}
  override fun needGlobalEvent(): Boolean {
    return false
  }

  @Subscribe(threadMode = MAIN) fun onMessageEvent(event: GlobalEvent) {
    /* Do something */
    for (s in eventList) {
      if (s == event.msgId) {
        eventComming(event)
        break
      }
    }
  }

  override fun onDestroy() {
    EventBus.getDefault().unregister(this)
    super.onDestroy()
  }
}