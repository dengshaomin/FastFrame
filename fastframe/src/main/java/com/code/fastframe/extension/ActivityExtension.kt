@file:JvmName("AndroidUtils")

package com.iqiyi.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.ViewCompat




fun Activity.toast(
    res: Int,
    duration: Int = Toast.LENGTH_LONG
) {
    val toast = Toast.makeText(this, res, duration)
    toast.show()
}

fun Activity.toast(
    title: CharSequence?,
    duration: Int = Toast.LENGTH_LONG
) {
    val toast = Toast.makeText(this, title, duration)
    toast.show()
}

inline fun <reified T> Activity.navigateActivity() {
    startActivity(Intent(this, T::class.java))
}

fun Activity.navigateActivity(action: String) {
    startActivity(Intent(action))
}

fun Activity.navigateActivity(
    action: String,
    intentBuilder: Intent.() -> Unit
) {
    startActivity(Intent(action).apply(intentBuilder))
}

fun Activity.navigateActivityForResult(
    action: String,
    requestCode: Int
) {
    startActivityForResult(Intent(action), requestCode)
}

fun Activity.navigateActivityForResult(
    action: String,
    requestCode: Int,
    intentBuilder: Intent.() -> Unit
) {
    startActivityForResult(Intent(action).apply(intentBuilder), requestCode)
}

inline fun <reified T> Activity.navigateActivityForResult(requestCode: Int) {
    startActivityForResult(Intent(this, T::class.java), requestCode)
}

inline fun <reified T> Activity.navigateActivity(intentBuilder: Intent.() -> Unit) {
    startActivity(Intent(this, T::class.java).apply(intentBuilder))
}

inline fun <reified T> Activity.navigateActivityForResult(
    requestCode: Int,
    intentBuilder: Intent.() -> Unit
) {
    startActivityForResult(Intent(this, T::class.java).apply(intentBuilder), requestCode)
}


fun Context.typeface(typeface: String): Typeface {
    return Typeface.createFromAsset(this.assets, typeface)
}

fun Activity.setRequestOrientationSafely(orientation: Int) {
    try {
        requestedOrientation = orientation
    } catch (e: Throwable) {
        e.printStackTrace()
        try {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } catch (err: Throwable) {
            err.printStackTrace()
        }

    }
}

fun Activity.setActivityFullScreen(immersive: Boolean) {
    var uiOption = window?.decorView?.systemUiVisibility ?: 0
    uiOption = if (immersive) {
        (uiOption
//        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    } else View.SYSTEM_UI_FLAG_VISIBLE
    window?.decorView?.systemUiVisibility = uiOption
}

fun Activity.showOrHideNavigationBar(
    isToHide: Boolean
) {
    val hideOptions =
        (View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE)
    val showOptions =
        (View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    val options = if (isToHide) hideOptions else showOptions
    val decorView = this.window.decorView
    decorView.systemUiVisibility = options
    if (!isToHide) {
        return
    }
    decorView.setOnSystemUiVisibilityChangeListener { visibility ->
        if (visibility == View.VISIBLE) {
            decorView.postDelayed(
                { decorView.systemUiVisibility = options }, 3000L
            )
        }
    }
}

fun Activity.setStatusBarColor(statusColor: Int) {
    val window: Window = window
    //取消状态栏透明
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    //添加Flag把状态栏设为可绘制模式
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    //设置状态栏颜色
    window.statusBarColor = statusColor
    //设置系统状态栏处于可见状态
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    //让view不根据系统窗口来调整自己的布局
    val mChildView = window.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT).getChildAt(0)
    if (mChildView != null) {
        ViewCompat.setFitsSystemWindows(mChildView, false)
        ViewCompat.requestApplyInsets(mChildView)
    }
}

private val SYSTEM_RESOURCES = Resources.getSystem()

fun getScreenWidth(): Int {
    return SYSTEM_RESOURCES.displayMetrics.widthPixels
}

fun getScreenHeight(): Int {
    return SYSTEM_RESOURCES.displayMetrics.heightPixels
}

fun dpToPixelF(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        SYSTEM_RESOURCES.displayMetrics
    )
}

fun dpToPixel(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        SYSTEM_RESOURCES.displayMetrics
    ).toInt()
}

fun spToPixel(sp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp.toFloat(),
        SYSTEM_RESOURCES.displayMetrics
    ).toInt()
}

fun spToPixelF(sp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        SYSTEM_RESOURCES.displayMetrics
    )
}

val Float.dp
    get() = dpToPixelF(this)

val Int.dp
    get() = dpToPixel(this)

val Int.sp
    get() = spToPixel(this)

val Float.sp
    get() = spToPixelF(this)