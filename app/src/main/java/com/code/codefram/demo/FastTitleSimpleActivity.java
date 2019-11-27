package com.code.codefram.demo;

import java.util.List;

import com.code.cframe.baseview.PageStateView.State;
import com.code.cframe.fastactivity.FastTitleActivity;
import com.code.codefram.R;
import com.code.codefram.demo.model.LogisticsModel;
import com.code.codefram.demo.presents.TestPresent;
import com.code.codefram.demo.presents.TestPresent.ITest;
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
        mTestPresent.getLogistics();
    }


    @Override
    public void showData(List<LogisticsModel> data) {
        mPageStateView.setViewData(State.SUCCESS);
    }

    @Override
    public void showError(String s) {
        mPageStateView.setViewData(State.ERROR);
    }
}
