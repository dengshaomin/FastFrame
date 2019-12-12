package com.code.fastframe.ciface;

import java.util.List;

import com.code.fastframe.eventbus.GlobalEvent;

public interface IBaseEvent {
    //eventbus需要注册的事件
    List<Integer> regeistEvent();
    //有注册过的事件消息到达
    void eventComming(GlobalEvent globalEvent);
    //是否需要关注消息
    boolean needGlobalEvent();
}
