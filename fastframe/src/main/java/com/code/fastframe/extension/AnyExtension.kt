package com.code.fastframe.extension

import android.os.Looper

fun isMainThread(): Boolean = Looper.getMainLooper().thread == Thread.currentThread()

