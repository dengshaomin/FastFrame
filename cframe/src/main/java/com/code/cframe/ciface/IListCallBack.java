package com.code.cframe.ciface;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dengshaomin on 2017/12/5.
 */

public interface IListCallBack {

    void onHeadFootClickLister(View view, Object data, int position);

    void onItemClickLister(View view, Object data, int position);

    void onRefresh(int page);

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(ViewHolder holder, int position);

}