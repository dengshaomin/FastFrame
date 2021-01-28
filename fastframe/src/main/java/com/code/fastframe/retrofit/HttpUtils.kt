package com.code.fastframe.retrofit

import android.content.Context
import com.alibaba.fastjson.JSON
import com.andview.refreshview.utils.LogUtils
import com.code.fastframe.retrofit.models.ServerModel

object HttpUtils {
  private const val TAG = "HttpUtils"
  @JvmOverloads fun init(
    context: Context?,
    mainHost: String? = null
  ) {
    requireNotNull(context) { "context not be null!" }
    ApiFactory.init(context, mainHost)
  }

  suspend fun <T> requestServer(
    block: suspend () -> ServerModel<T>
  ): ServerResponse<T> {
    return try {
      val response = block()
      LogUtils.e(JSON.toJSONString(response.data))
      ServerResponse.success(response)
    } catch (e: Throwable) {
      e.printStackTrace()
      ServerResponse.error(e, null)
    }
  }
}