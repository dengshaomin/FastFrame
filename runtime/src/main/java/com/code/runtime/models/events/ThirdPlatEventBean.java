package com.code.runtime.models.events;

public class ThirdPlatEventBean {
    public int plat;
    public String type;
    public int result;
    public String msg;
    public ThirdPlatEventBean(int plat, String type, int result,String msg) {
        this.plat = plat;
        this.type = type;
        this.result = result;
        this.msg = msg;
    }
}
