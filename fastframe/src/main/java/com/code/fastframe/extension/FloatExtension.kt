package com.code.fastframe.extension

import com.code.fastframe.utils.DensityUtil

fun Float.dp(): Int {
  return DensityUtil.dip2px(this)
}

fun Float.sp(): Int {
  return DensityUtil.sp2px(this)
}


