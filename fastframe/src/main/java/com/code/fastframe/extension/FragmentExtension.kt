package com.iqiyi.extension

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


const val TAG = "FragmentExtensions"

fun Fragment.toast(
  res: Int,
  duration: Int = Toast.LENGTH_LONG
) {
  activity?.let {
    val toast = Toast.makeText(it, res, duration)
    toast.show()
  }
}

fun Fragment.shortToast(
  res: Int,
  duration: Int = Toast.LENGTH_SHORT
) {
  activity?.let {
    val toast = Toast.makeText(it, res, duration)
    toast.show()
  }
}

fun Fragment.toast(
  text: CharSequence,
  duration: Int = Toast.LENGTH_LONG
) {
  activity?.let {
    val toast = Toast.makeText(it, text, duration)
    toast.show()
  }
}

fun Fragment.shortToast(
  text: CharSequence,
  duration: Int = Toast.LENGTH_SHORT
) {
  activity?.let {
    val toast = Toast.makeText(it, text, duration)
    toast.show()
  }
}

inline fun <reified T> Fragment.navigateActivity() {
  context?.let {
    startActivity(Intent(it, T::class.java))
  }
}

inline fun <reified T> Fragment.navigateActivityForResult(requestCode: Int) {
  context?.let {
    startActivityForResult(Intent(it, T::class.java), requestCode)
  }

}

inline fun <reified T> Fragment.navigateActivityForResult(
  requestCode: Int,
  intentBuilder: Intent.() -> Unit
) {
  context?.let {
    startActivityForResult(
      Intent(it, T::class.java).apply(intentBuilder),
      requestCode
    )
  }
}

inline fun <reified T> Fragment.navigateActivity(intentBuilder: Intent.() -> Unit) {
  context?.let {
    startActivity(Intent(it, T::class.java).apply(intentBuilder))
  }
}

inline fun <reified T> Fragment.navigateActivity(bundle: Bundle) {
  context?.let {
    startActivity(Intent(it, T::class.java), bundle)
  }
}

inline fun <reified T : Fragment> FragmentManager.commitOnce(
  containerId: Int = android.R.id.content,
  creator: () -> T,
  transaction: FragmentTransaction.() -> FragmentTransaction
) {

  val fragmentTag = T::class.java.name
  if (findFragmentByTag(fragmentTag) is T) {
    Log.w(TAG, "There is already a fragment instance.")
    return
  }

  beginTransaction()
    .transaction()
    .add(containerId, creator(), fragmentTag)
    .addToBackStack(fragmentTag)
    .commit()
  executePendingTransactions()
}

inline fun <reified T : Fragment> FragmentManager.commitAllowingStateLossOnce(
  containerId: Int = android.R.id.content,
  creator: () -> T,
  transaction: FragmentTransaction.() -> FragmentTransaction
) {

  val fragmentTag = T::class.java.name
  if (findFragmentByTag(fragmentTag) is T) {
    Log.w(TAG, "There is already a fragment instance.")
    return
  }

  beginTransaction()
    .transaction()
    .add(containerId, creator(), fragmentTag)
    .addToBackStack(fragmentTag)
    .commitAllowingStateLoss()
  executePendingTransactions()
}

inline fun <reified T : Fragment> FragmentManager.commitNowOnce(
  containerId: Int = android.R.id.content,
  creator: () -> T,
  transaction: FragmentTransaction.() -> FragmentTransaction
) {
  executePendingTransactions()
  val fragmentTag = T::class.java.name
  if (findFragmentByTag(fragmentTag) is T) {
    Log.w(TAG, "There is already a fragment instance.")
    return
  }

  beginTransaction()
    .transaction()
    .add(containerId, creator(), fragmentTag)
    .addToBackStack(fragmentTag)
    .commitNow()
}

inline fun <reified T : Fragment> FragmentManager.commitNowAllowingStateLossOnce(
  containerId: Int = android.R.id.content,
  creator: () -> T,
  transaction: FragmentTransaction.() -> FragmentTransaction
) {
  executePendingTransactions()
  val fragmentTag = T::class.java.name
  if (findFragmentByTag(fragmentTag) is T) {
    Log.w(TAG, "There is already a fragment instance.")
    return
  }

  beginTransaction()
    .transaction()
    .add(containerId, creator(), fragmentTag)
    .addToBackStack(fragmentTag)
    .commitNowAllowingStateLoss()
}