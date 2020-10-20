package com.code.fastframe.ciface;

import androidx.recyclerview.widget.RecyclerView.ItemDecoration;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import android.view.View;

import com.code.fastframe.baseview.BaseItemLayout;

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
