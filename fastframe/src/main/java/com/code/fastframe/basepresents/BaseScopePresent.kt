package com.code.fastframe.basepresents

import android.content.Context
import com.code.fastframe.extension.getActivityScope
import kotlinx.coroutines.CoroutineScope

open class BaseScopePresent(context: Context?) {
  var scope: CoroutineScope

  init {
    scope = getActivityScope(context)
  }

}