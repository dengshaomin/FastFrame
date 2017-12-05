package com.code.codeframlibrary.commons;

/**
 * Created by dengshaomin on 2017/11/7.
 */

public class GlobalMsg {
    private String msgId;
    private Object msg;
    private boolean success;

    public GlobalMsg(boolean success, String msgId, Object msg) {
        this.success = success;
        this.msgId = msgId;
        this.msg = msg;
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

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
