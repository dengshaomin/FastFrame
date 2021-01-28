package com.code.fastframe.fastactivity

import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import com.code.fastframe.baseactivity.BaseTitleActivity
import com.code.fastframe.baseview.PageStateView
import com.code.fastframe.ciface.IPageStateCb
import kotlinx.android.synthetic.main.activity_title_base.container

/**
 * 快速创建带状态页面的activity
 */
abstract class FastTitleActivity : BaseTitleActivity(), IPageStateCb {
  var mPageStateView: PageStateView? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun initView() {
    mPageStateView = PageStateView(this, this)
    container.addView(
        mPageStateView,
        LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    )
  }

  override fun setTitleText(): String {
    return this.javaClass.simpleName
  }

  override fun pageStateViewClick(pageState: Int) {}
}