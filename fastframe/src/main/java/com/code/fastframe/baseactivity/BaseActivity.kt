package com.code.fastframe.baseactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.code.fastframe.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

open class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onDestroy() {
    super.onDestroy()
    cancel()
  }
}