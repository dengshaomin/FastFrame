package com.code.fastframe.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.code.fastframe.R;

/**
 * Created by dengshaomin on 2017/7/25.
 */

public class BaseRcvFooterView extends BaseViewLayout {

    private View progress, no_more_tip;

    public BaseRcvFooterView(Context context) {
        super(context);
    }

    public BaseRcvFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRcvFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int setContentLayout() {
        return R.layout.view_basercv_footer;
    }

    @Override
    public void initView() {
        progress = findViewById(R.id.progress);
        no_more_tip = findViewById(R.id.no_more_tip);
    }

    @Override
    public void setViewData(Object data) {
        if (data instanceof Boolean && (Boolean) data) {
            no_more_tip.setVisibility(GONE);
            progress.setVisibility(VISIBLE);
        } else {
            no_more_tip.setVisibility(VISIBLE);
            progress.setVisibility(GONE);
        }
    }
}
