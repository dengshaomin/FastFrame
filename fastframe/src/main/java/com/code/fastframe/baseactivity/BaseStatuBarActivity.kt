package com.code.fastframe.baseactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.code.fastframe.R.color
import com.code.fastframe.ciface.IBaseStatuBarActivity
import com.code.fastframe.utils.StatusBarUtil

abstract class BaseStatuBarActivity : AppCompatActivity(), IBaseStatuBarActivity {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onResume() {
    super.onResume()
    if (needNavigationBarTransparent()) {
      //            StatusBarUtil.setTransparent(this);
    }
    if (needStatuBarTransparent()) {
      StatusBarUtil.setColor(this, resources.getColor(color.titleBarColor), 0)
    }
  }

  override fun needStatuBarTransparent(): Boolean {
    return false
  }

  override fun needNavigationBarTransparent(): Boolean {
    return false
  }
}