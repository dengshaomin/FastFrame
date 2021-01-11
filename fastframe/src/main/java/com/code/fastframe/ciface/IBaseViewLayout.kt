package com.code.fastframe.ciface

/**
 * Created by dengshaomin on 2016/10/21.
 */
interface IBaseViewLayout<T> : IBaseLayout {
  fun setViewData(data: T)
}