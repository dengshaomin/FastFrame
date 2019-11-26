package com.code.cframe.activity;

import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;

import com.code.cframe.R;
import com.code.cframe.baseview.PageStateView;
import com.code.cframe.ciface.IPageStateCb;

public class FastPageStateActivity extends BaseTitleActivity implements IPageStateCb {

    protected PageStateView mPageStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_fast_page_state;
    }

    @Override
    public void initView() {
        mPageStateView = new PageStateView(this, this);
        container.addView(mPageStateView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    @Override
    public String setTitleText() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void pageStateViewClick(int pageState) {

    }
}
