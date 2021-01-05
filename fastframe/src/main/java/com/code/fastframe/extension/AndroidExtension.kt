package com.iqiyi.extension

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.*
import com.code.fastframe.extension.isMainThread

private val mainThreadHandler = Handler(Looper.getMainLooper())



fun Any.runOnUiThread(runnable: () -> Unit) {
    if (isMainThread()) {
        runnable.invoke()
    } else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mainThreadHandler.postDelayed(runnable, this, 0)
        } else {
            mainThreadHandler.postAtTime(runnable, this, SystemClock.uptimeMillis())
        }
    }
}

fun Any.runOnUiThread(runnable: Runnable) {
    if (isMainThread()) {
        runnable.run()
    } else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mainThreadHandler.postDelayed(runnable, this, 0)
        } else {
            mainThreadHandler.postAtTime(runnable, this, SystemClock.uptimeMillis())
        }
    }
}

fun Any.delayOnUiThread(
  millis: Long,
  runnable: Runnable
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        mainThreadHandler.postDelayed(runnable, this, millis)
    } else {
        mainThreadHandler.postAtTime(runnable, this, SystemClock.uptimeMillis() + millis)
    }
}

fun Any.delayOnUiThread(
  millis: Long,
  runnable: () -> Unit
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        mainThreadHandler.postDelayed(runnable, this, millis)
    } else {
        mainThreadHandler.postAtTime(runnable, this, SystemClock.uptimeMillis() + millis)
    }
}

fun Any.removeCallbacks(runnable: () -> Unit) {
    mainThreadHandler.removeCallbacks(runnable)
}

fun Any.removeCallbacks(runnable: Runnable) {
    mainThreadHandler.removeCallbacks(runnable)
}

fun Any.removeCallbacksAndMessages() {
    mainThreadHandler.removeCallbacksAndMessages(this)
}

fun Float.isEqualZero(): Boolean {
    if (this >= 0.00001f || this <= -0.00001f) {
        return false
    }
    return true
}

fun getProcessName(cxt: Context): String? {
    val pid = Process.myPid()
    val am = cxt.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningApps = am.runningAppProcesses ?: return null
    for (procInfo in runningApps) {
        if (procInfo.pid == pid) {
            return procInfo.processName
        }
    }
    return null
}

/**
 * 判断某个activity是否在栈中
 * */
fun Context.isExistActivity(
  activity: Class<*>
): Boolean {
    val intent = Intent(this, activity)
    val cmpName = intent.resolveActivity(this.packageManager)
    var flag = false
    if (cmpName != null) { // 说明系统中存在这个activity    
        val am =
            this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val taskInfoList = am.getRunningTasks(10) //
        for (taskInfo in taskInfoList) {
            if (taskInfo.baseActivity == cmpName) { // 说明它已经启动了
                flag = true
                break //跳出循环，优化效率
            }
        }
    }
    return flag
}

fun <T : Parcelable> copyTo(origin: T?): T? {
    var parcel: Parcel? = null

    try {
        parcel = Parcel.obtain()
        parcel.writeParcelable(origin, 0)

        parcel.setDataPosition(0)
        return parcel.readParcelable(origin?.javaClass?.classLoader)
    } finally {
        parcel?.recycle()
    }
}


