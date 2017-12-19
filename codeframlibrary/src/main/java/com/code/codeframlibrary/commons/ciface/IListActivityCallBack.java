package com.code.codeframlibrary.commons.ciface;

import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;

import com.code.codeframlibrary.commons.baseview.BaseItemLayout;

/**
 * Created by dengshaomin on 2017/12/18.
 */

public interface IListActivityCallBack {

    ItemDecoration addItemDecoration();

    LayoutManager setLayoutManager();

    boolean needStateView();

    int setRefreshMode();

    int setSpringBackMode();

    BaseItemLayout getListItemView();

    void onItemClickLister(View view, Object data, int position);
}
