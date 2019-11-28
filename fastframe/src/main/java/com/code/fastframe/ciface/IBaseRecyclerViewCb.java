package com.code.fastframe.ciface;

import android.view.View;

/**
 * Created by dengshaomin on 2017/12/5.
 */

public interface IBaseRecyclerViewCb {

    void onHeadFootClickLister(View view, Object data, int position);

    void onRefresh(int currentState);
}
