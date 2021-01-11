package com.code.fastframe.baseactivity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.code.fastframe.R;
import com.code.fastframe.ciface.IBaseStatuBarActivity;
import com.code.fastframe.utils.StatusBarUtil;

public abstract class BaseStatuBarActivity extends AppCompatActivity
    implements IBaseStatuBarActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (needNavigationBarTransparent()) {
      //            StatusBarUtil.setTransparent(this);
    }
    if (needStatuBarTransparent()) {
      StatusBarUtil.setColor(this, getResources().getColor(R.color.titleBarColor), 0);
    }
  }

  @Override
  public boolean needStatuBarTransparent() {
    return false;
  }

  @Override
  public boolean needNavigationBarTransparent() {
    return false;
  }
}
