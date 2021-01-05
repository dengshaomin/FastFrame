package com.iqiyi.extension

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build.VERSION_CODES
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import com.code.fastframe.R

val View.layoutInflater: LayoutInflater
  get() = LayoutInflater.from(this.context)

fun View.inflater(): LayoutInflater = LayoutInflater.from(context)

fun View.inflater(layoutRes: Int): View {
  return LayoutInflater.from(context).inflate(layoutRes, null)
}

fun ViewGroup.inflater(
    layoutRes: Int,
    attachToRoot: Boolean = false
): View {
  return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun View.createFontFromAssets(path: String): Typeface {
  return Typeface.createFromAsset(this.context.assets, path)
}

fun View.setMargin(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
) {
  val lp = layoutParams
  if (lp == null) {
    Log.w("ViewExtensions", "View doesn't have a layoutParams")
    return
  }
  if (lp !is ViewGroup.MarginLayoutParams) {
    Log.w("ViewExtensions", "View doesn't have a MarginLayoutParams")
    return
  }
  lp.leftMargin = left
  lp.rightMargin = right
  lp.topMargin = top
  lp.bottomMargin = bottom
  layoutParams = lp
}

fun throttleClick(
    wait: Long = 200,
    block: ((View) -> Unit)
): View.OnClickListener {

  return View.OnClickListener { v ->
    val current = System.currentTimeMillis()
    val lastClickTime = (v.getTag(R.id.click_timestamp) as? Long) ?: 0
    if (current - lastClickTime > wait) {
      block(v)
      v.setTag(R.id.click_timestamp, current)
    }
  }
}

fun debounceClick(
    wait: Long = 200,
    block: ((View) -> Unit)
): View.OnClickListener {
  return View.OnClickListener { v ->
    var action = (v.getTag(R.id.click_debounce_action) as? DebounceAction)
    if (action == null) {
      action = DebounceAction(v, block)
      v.setTag(R.id.click_debounce_action, action)
    } else {
      action.block = block
    }
    v.removeCallbacks(action)
    v.postDelayed(action, wait)
  }
}

class DebounceAction(
    val view: View,
    var block: ((View) -> Unit)
) : Runnable {
  @RequiresApi(VERSION_CODES.KITKAT)
  override fun run() {
    if (view.isAttachedToWindow) {
      block(view)
    }
  }
}

fun View.onClick(
    wait: Long = 200,
    block: ((View) -> Unit)
) {
  setOnClickListener(throttleClick(wait, block))
}

fun View.onClick(
    wait: Long = 200,
    onClickListener: View.OnClickListener
) {
  setOnClickListener {
    val current = System.currentTimeMillis()
    val lastClickTime = (this.getTag(R.id.click_timestamp) as? Long) ?: 0
    if (current - lastClickTime > wait) {
      onClickListener.onClick(this)
      this.setTag(R.id.click_timestamp, current)
    }
  }
}

fun View.onDebounceClick(
    wait: Long = 200,
    block: ((View) -> Unit)
) {
  setOnClickListener(debounceClick(wait, block))
}

fun View.loadBitmap(): Bitmap? {
  if (width <= 0 || height <= 0) return null
  val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
  val canvas = Canvas(bmp)
  canvas.drawColor(Color.BLACK)
  draw(canvas)
  return bmp
}

fun ViewGroup.findMaxElevation(
    excludeView: View
): Float {
  val childCount = childCount
  var max = 0f
  for (i in 0 until childCount) {
    val child = getChildAt(i)
    if (child === excludeView) {
      continue
    }
    val elevation = ViewCompat.getElevation(child)
    if (elevation > max) {
      max = elevation
    }
  }
  return max
}

fun View.toast(
    res: Int,
    duration: Int = Toast.LENGTH_SHORT
) {
  context?.let {
    val toast = Toast.makeText(it, res, duration)
    toast.show()
  }

}

fun View.toast(
    text: CharSequence,
    duration: Int = Toast.LENGTH_SHORT
) {
  context?.let {
    val toast = Toast.makeText(it, text, duration)
    toast.show()
  }
}