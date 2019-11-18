package com.code.cframe.ciface;

import java.util.List;

import com.code.cframe.GlobalMsg;


/**
 * Created by dengshaomin on 2016/10/21.
 */
public interface IBaseLayout {
    public int setContentLayout();

    public void initView();

    public void initBundleData();

    public void getNetData();

    public List<String> regeistEvent();

    public void eventComming(GlobalMsg globalMsg);

    public void setViewData(Object data);
}
