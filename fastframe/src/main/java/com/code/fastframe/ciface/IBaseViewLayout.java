package com.code.fastframe.ciface;


import android.view.View;

/**
 * Created by dengshaomin on 2016/10/21.
 */
public interface IBaseViewLayout<T> extends IBaseLayout {

    void setViewData(T data);

    View findView(int id);
}
