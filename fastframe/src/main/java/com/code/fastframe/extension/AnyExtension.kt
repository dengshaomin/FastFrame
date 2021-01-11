package com.code.fastframe.extension

import android.content.Context
import android.os.Looper
import com.andview.refreshview.utils.LogUtils
import com.code.fastframe.FastFrame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

fun isMainThread(): Boolean = Looper.getMainLooper().thread == Thread.currentThread()

fun Any.getActivityScope(context: Context?): CoroutineScope {
  context?.let {
    if (context is CoroutineScope) {
      return context
    }
  }
  return MainScope()
}

inline fun <reified T, R> T.tryOrNull(block: T.() -> R): R? {
  try {
    return block()
  } catch (e: Exception) {
    LogUtils.e(e.toString())
  }
  return null
}

fun getResourceString(resourceId: Int): String {
  FastFrame.mApplicationContext?.let {
    return FastFrame.mApplicationContext!!.getString(resourceId)
  }
  return ""
}
