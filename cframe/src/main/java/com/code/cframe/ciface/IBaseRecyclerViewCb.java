package com.code.cframe.ciface;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dengshaomin on 2017/12/5.
 */

public interface IBaseRecyclerViewCb {

    void onHeadFootClickLister(View view, Object data, int position);

    void onRefresh(int currentState);
}
