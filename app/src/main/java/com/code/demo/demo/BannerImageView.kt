package com.code.demo.demo

import android.content.Context
import android.util.AttributeSet
import com.code.demo.R
import com.code.demo.R.layout
import com.code.fastframe.baseview.BaseViewLayout
import com.code.fastframe.utils.ImageUtils
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.view_banner_image.view.image

class BannerImageView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BaseViewLayout(context, attrs, defStyleAttr) {

  override fun setContentLayout(): Int {
    return layout.view_banner_image
  }

  override fun initView() {
  }

  override fun setViewData(data: Any?) {
    ImageUtils.getInstance().loadImage(image, data.toString() + "")
  }
}