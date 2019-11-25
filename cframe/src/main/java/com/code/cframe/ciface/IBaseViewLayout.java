package com.code.cframe.ciface;


import android.view.View;

/**
 * Created by dengshaomin on 2016/10/21.
 */
public interface IBaseViewLayout<T> extends IBaseLayout {


    void initData();

    void setViewData(T data);

    View findView(int id);
}
