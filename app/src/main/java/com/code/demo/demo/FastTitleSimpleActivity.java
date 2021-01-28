package com.code.demo.demo;

import com.code.demo.R;
import com.code.demo.demo.model.LogisticsModel;
import com.code.demo.demo.presents.TestPresent;
import com.code.demo.demo.presents.TestPresent.ITest;
import com.code.fastframe.baseview.PageStateView.State;
import com.code.fastframe.fastactivity.FastTitleActivity;
import java.util.List;
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
  public void requestNetData() {
    super.requestNetData();
    mTestPresent.logistics();
  }

  @Override
  public void showError(String s) {
    getMPageStateView().setCurrentState(State.ERROR);
  }

  @Override public void pageStateViewClick(int pageState) {
    super.pageStateViewClick(pageState);
    requestNetData();
  }

  @Override public void showData(@Nullable List<? extends LogisticsModel> data) {
    getMPageStateView().setCurrentState(State.SUCCESS);
  }
}
