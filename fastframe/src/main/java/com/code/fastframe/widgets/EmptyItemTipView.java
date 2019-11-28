package com.code.fastframe.widgets;

import android.content.Context;
import android.util.AttributeSet;

import com.code.fastframe.R;
import com.code.fastframe.baseview.BaseViewLayout;

/**
 * Created by dengshaomin on 2017/12/19.
 */

public class EmptyItemTipView extends BaseViewLayout {

    public EmptyItemTipView(Context context) {
        super(context);
    }

    public EmptyItemTipView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyItemTipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int setContentLayout() {
        return R.layout.view_empty_tip_view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setViewData(Object data) {

    }
}
