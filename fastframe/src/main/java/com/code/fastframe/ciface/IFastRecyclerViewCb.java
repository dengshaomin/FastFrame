package com.code.fastframe.ciface;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dengshaomin on 2017/12/5.
 */

public interface IFastRecyclerViewCb extends IBaseRecyclerViewCb {

    void onItemClickLister(View view, Object data, int position);

    ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(ViewHolder holder, int position);

}
