package com.code.fastframe.fastactivity;

import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import com.code.fastframe.baseactivity.BaseTitleActivity;
import com.code.fastframe.baseview.PageStateView;
import com.code.fastframe.ciface.IPageStateCb;

/**
 * 快速创建带状态页面的activity
 */
public abstract class FastTitleActivity extends BaseTitleActivity implements IPageStateCb {

  protected PageStateView mPageStateView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void initView() {
    mPageStateView = new PageStateView(this, this);
    container.addView(mPageStateView,
        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
  }

  @Override
  public String setTitleText() {
    return this.getClass().getSimpleName();
  }

  @Override
  public void pageStateViewClick(int pageState) {

  }
}
