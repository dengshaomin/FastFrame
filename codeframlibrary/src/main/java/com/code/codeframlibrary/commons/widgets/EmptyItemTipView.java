package com.code.codeframlibrary.commons.widgets;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;

import com.code.codeframlibrary.R;
import com.code.codeframlibrary.commons.GlobalMsg;
import com.code.codeframlibrary.commons.baseview.BaseLayout;

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
    public void eventComming(GlobalMsg globalMsg) {

    }

    @Override
    public void setViewData(Object data) {

    }
}
