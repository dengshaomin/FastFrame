package com.code.fastframe.baseactivity

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.code.fastframe.R.layout
import com.code.fastframe.ciface.IBaseLayout
import com.code.fastframe.ciface.ITitle
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_title_base.container
import kotlinx.android.synthetic.main.activity_title_base.left_image
import kotlinx.android.synthetic.main.activity_title_base.nav_status_space
import kotlinx.android.synthetic.main.activity_title_base.right_image
import kotlinx.android.synthetic.main.activity_title_base.title_text

/**
 * Created by dengshaomin on 2017/11/7.
 */
abstract class BaseTitleActivity : BaseBundleActivity(), IBaseLayout, ITitle {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_title_base)
    initTitle()
    initView()
    initData()
    requestNetData()
  }

  open fun requestNetData(){

  }

  override fun initData() {}
  private fun initTitle() {
    if (setContentLayout() != 0) {
      LayoutInflater.from(this).inflate(setContentLayout(), container)
    }
    left_image.setOnClickListener(View.OnClickListener { titleLeftClick() })
    right_image.setOnClickListener(View.OnClickListener { titleRightClick() })
    title_text.setText(if (TextUtils.isEmpty(setTitleText())) "" else setTitleText())
    if (setTitleLeftImage() > 0) {
      left_image.setBackgroundResource(setTitleLeftImage())
    }
    if (setTitleRightImage() > 0) {
      left_image.setBackgroundResource(setTitleRightImage())
    }
    if (needImmerse()) {
      val layoutParams = nav_status_space.getLayoutParams()
      layoutParams.height = ImmersionBar.getStatusBarHeight(this)
      nav_status_space.setLayoutParams(layoutParams)
      ImmersionBar.with(this).init()
    }
  }

  override fun titleLeftClick() {
    onBackPressed()
  }

  override fun setTitleLeftImage(): Int {
    return 0
  }

  override fun setTitleRightImage(): Int {
    return 0
  }

  override fun titleRightClick() {}
  override fun needImmerse(): Boolean {
    return false
  }

  override fun onDestroy() {
    super.onDestroy()
  }
}