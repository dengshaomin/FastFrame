package com.code.fastframe.baseview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.code.fastframe.ciface.IBaseViewLayout

/**
 * Created by dengshaomin on 2016/10/21.
 */
abstract class BaseViewLayout @JvmOverloads constructor(
  context: Context?,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayout(
    context, attrs, defStyleAttr
), IBaseViewLayout<Any?> {

  override fun initData() {}
  override fun setViewData(data: Any?) {
  }

  init {
    val layoutId = setContentLayout()
    if (layoutId == 0) {
      throw RuntimeException("setLayout first~~")
    }
    inflate(context, layoutId, this)
    initView()
    initData()
  }
}