package com.code.fastframe.extension

import android.content.Context
import android.os.Looper
import android.widget.Toast
import com.andview.refreshview.utils.LogUtils
import com.code.fastframe.FastFrame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

fun getApplicationContext(): Context? {
  return FastFrame.mApplicationContext
}

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
  } catch (e: Throwable) {
    e?.let {
      LogUtils.e(e.toString())
    }
  }
  return null
}

fun getResourceString(resourceId: Int): String {
  FastFrame.mApplicationContext?.let {
    return FastFrame.mApplicationContext!!.getString(resourceId)
  }
  return ""
}

fun getResourceColor(resourceId: Int): Int {
  FastFrame.mApplicationContext?.let {
    return FastFrame.mApplicationContext!!.resources.getColor(resourceId)
  }
  return 0
}

fun toast(
  res: Int,
  duration: Int = Toast.LENGTH_LONG
) {
  toast(getResourceString(res), duration)
}

fun toast(
  title: CharSequence?,
  duration: Int = Toast.LENGTH_LONG
) {
  if (isMainThread()) {
    Toast.makeText(getApplicationContext(), title, duration).show()
  } else {
    MainScope().launch {
      Toast.makeText(getApplicationContext(), title, duration).show()
    }
  }
}

fun currentTimeMillis(): Long {
  return System.currentTimeMillis()
}






