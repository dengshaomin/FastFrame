package com.code.demo.activity

import android.content.Intent
import android.os.Bundle
import com.code.demo.R
import com.code.demo.R.layout
import com.code.demo.demo.MainActivity
import com.code.fastframe.baseactivity.BasePermissionActivity
import com.iqiyi.extension.navigateActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WelcomeActivity : BasePermissionActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    //防止点击app图标，发现应用重启了
    if (!this.isTaskRoot) {
      val intent = intent
      if (intent != null) {
        val action = intent.action
        if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
          finish()
        }
      }
    }
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_welcome)
    GlobalScope.launch {
      try {
        Thread.sleep(10)
      } catch (e: InterruptedException) {
        e.printStackTrace()
      }
      navigateActivity<MainActivity>()
      overridePendingTransition(R.anim.activity_open_enter,R.anim.activity_open_exit)
      finish()
    }
  }
}