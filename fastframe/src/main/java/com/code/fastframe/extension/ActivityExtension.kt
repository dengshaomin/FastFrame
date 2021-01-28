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
