package com.code.demo.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import com.code.demo.R;
import com.code.fastframe.FastFrame;
import com.code.fastframe.baseactivity.BaseTitleActivity;
import com.code.fastframe.utils.ToastUtils;

public class HomeActivity extends BaseTitleActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public int setContentLayout() {
    return R.layout.activity_home;
  }

  @Override
  public void initView() {

  }

  @Override
  public String setTitleText() {
    return this.getClass().getSimpleName();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    FastFrame.getInstance().onDestory();
  }

  private long firstTime = 0;

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
      if (System.currentTimeMillis() - firstTime > 2000) {
        ToastUtils.showToast("再按一次退出程序~");
        firstTime = System.currentTimeMillis();
      } else {
        finish();
      }
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }
}
