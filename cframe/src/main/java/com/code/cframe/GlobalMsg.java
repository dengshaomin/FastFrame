package com.code.cframe;

/**
 * Created by dengshaomin on 2017/11/7.
 * eventbus消息通信实体
 */
public class GlobalMsg {

    //消息ID
    private String msgId;

    //消息携带内容
    private Object data;

    //任务是否处理成功
    private boolean success;

    public GlobalMsg(boolean success, String msgId, Object data) {
        this.success = success;
        this.msgId = msgId;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
