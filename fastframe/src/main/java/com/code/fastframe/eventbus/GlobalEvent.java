package com.code.fastframe.eventbus;

/**
 * Created by dengshaomin on 2017/11/7.
 * eventbus消息通信实体
 */
public class GlobalEvent {

    //消息ID
    private int msgId;

    //消息携带内容
    private Object data;

    //任务是否处理成功
    private boolean success;

    public GlobalEvent(boolean success, int msgId, Object data) {
        this.success = success;
        this.msgId = msgId;
        this.data = data;
    }

    public GlobalEvent(int msgId, Object data) {
        this(true, msgId, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
