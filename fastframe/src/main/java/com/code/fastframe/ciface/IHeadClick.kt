package com.code.fastframe.ciface

import android.view.View

/**
 * Created by dengshaomin on 2017/12/5.
 */
interface IHeadClick {
  fun onHeadFootClickLister(
    view: View?,
    data: Any?,
    position: Int
  )
}