package com.code.cframe.ciface;

public interface IPageStateView {

    void setEmptyResource(int emptyResource);

    void setErrorResource(int errorResource);

    void setNetResource(int netResource);

    void setErrorTip(String errorTip);

    void setEmptyTip(String emptyTip);

    void setLoadingTip(String loadingTip);

    void setNetTip(String netTip);
}
