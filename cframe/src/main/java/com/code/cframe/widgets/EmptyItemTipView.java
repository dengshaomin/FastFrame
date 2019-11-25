package com.code.cframe.widgets;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;

import com.code.cframe.R;
import com.code.cframe.GlobalEvent;
import com.code.cframe.baseview.BaseLayout;

/**
 * Created by dengshaomin on 2017/12/19.
 */

public class EmptyItemTipView extends BaseLayout {

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
    public void initBundleData() {

    }

    @Override
    public void getNetData() {

    }

    @Override
    public List<String> regeistEvent() {
        return null;
    }

    @Override
    public void eventComming(GlobalEvent globalMsg) {

    }

    @Override
    public void setViewData(Object data) {

    }
}
