package com.code.demo.demo;

import java.util.List;

import com.code.demo.demo.model.LogisticsModel;
import com.code.demo.demo.presents.TestPresent;
import com.code.demo.demo.presents.TestPresent.ITest;
import com.code.fastframe.baseview.PageStateView.State;
import com.code.fastframe.fastactivity.FastTitleActivity;
import com.code.demo.R;
import org.jetbrains.annotations.Nullable;

public class FastTitleSimpleActivity extends FastTitleActivity implements ITest {

    TestPresent mTestPresent;

    @Override
    public int setContentLayout() {
        return R.layout.activity_fast_title_simple;
    }

    @Override
    public void initData() {
        super.initData();
        mTestPresent = new TestPresent(this, this);
    }

    @Override
    public void getNetData() {
        super.getNetData();
        mTestPresent.logistics();
    }


    @Override
    public void showError(String s) {
        mPageStateView.setViewData(State.ERROR);
    }

    @Override public void pageStateViewClick(int pageState) {
        super.pageStateViewClick(pageState);
        getNetData();
    }

    @Override public void showData(@Nullable List<? extends LogisticsModel> data) {
        mPageStateView.setViewData(State.SUCCESS);
    }
}
