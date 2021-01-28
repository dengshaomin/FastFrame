package com.code.fastframe.baseactivity

import android.os.Bundle
import com.code.fastframe.ciface.IBaseBundle
import com.code.fastframe.eventbus.GlobalEvent

abstract class BaseBundleActivity : BaseEventActivity(), IBaseBundle {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    getBunleData()
  }

  override fun getBunleData() {}
  override fun eventComming(globalMsg: GlobalEvent) {
    super.eventComming(globalMsg)
  }
}