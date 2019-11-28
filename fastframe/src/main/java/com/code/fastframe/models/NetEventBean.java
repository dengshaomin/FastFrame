package com.code.fastframe.models;

public class NetEventBean {
    private int lastStatu;
    private int currentStatu;

    public NetEventBean(int lastStatu, int currentStatu) {
        this.lastStatu = lastStatu;
        this.currentStatu = currentStatu;
    }

    public int getLastStatu() {
        return lastStatu;
    }

    public void setLastStatu(int lastStatu) {
        this.lastStatu = lastStatu;
    }

    public int getCurrentStatu() {
        return currentStatu;
    }

    public void setCurrentStatu(int currentStatu) {
        this.currentStatu = currentStatu;
    }
}
