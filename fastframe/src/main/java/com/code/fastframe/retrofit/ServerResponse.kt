package com.code.fastframe.retrofit

import com.code.fastframe.FastFrame
import com.code.fastframe.R
import com.code.fastframe.extension.getResourceString
import com.code.fastframe.retrofit.ServerStatus.FAIL
import com.code.fastframe.retrofit.ServerStatus.SUCCESS
import com.code.fastframe.retrofit.error.ErrorType.DEFAULT
import com.code.fastframe.retrofit.error.ErrorType.NET
import com.code.fastframe.retrofit.error.ErrorType.SERVER
import com.code.fastframe.retrofit.error.HttpError
import com.code.fastframe.retrofit.models.ServerModel
import com.code.fastframe.utils.NetWorkUtils

class ServerResponse<T>(
  val statu: ServerStatus,
  val data: T?,
  val httpError: HttpError?
) {
  fun success():Boolean{
    return statu ==ServerStatus.SUCCESS
  }
  companion object {
    fun <T> success(data: ServerModel<T>?): ServerResponse<T> {
      data?.let {
        if (!it.success()) {
          return error(null, it)
        }
      }
      return ServerResponse(SUCCESS, data?.data, null)
    }

    fun <T> error(
      throwable: Throwable?,
      data: ServerModel<T>?
    ): ServerResponse<T> {
      var msg: String? = null
      var httpError: HttpError? = null
      if (!NetWorkUtils.connected(FastFrame.mApplicationContext)) {
        msg = getResourceString(R.string.net_off)
        httpError = HttpError(NET, null, msg, null)
      } else if (data != null) {
        msg = data.msg
        httpError = HttpError(SERVER, data.code, data.msg, null)
      } else {
        msg = if (throwable != null) throwable.message else getResourceString(
            R.string.ukonw_exception
        )
        httpError = HttpError(
            DEFAULT, null, msg, null
        )
      }
      return ServerResponse(FAIL, data?.data, httpError)
    }
  }
}