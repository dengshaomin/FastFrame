package com.code.demo.demo.presents

import android.content.Context
import com.code.demo.apis.APIInit
import com.code.demo.demo.model.LogisticsModel
import com.code.fastframe.basepresents.BaseScopePresent
import com.code.fastframe.ciface.IBasePresent
import com.code.fastframe.retrofit.HttpUtils.requestServer
import kotlinx.coroutines.launch

class TestPresent(
  context: Context?,
  private val mITest: ITest?
) : BaseScopePresent(
    context
), IBasePresent {
  fun logistics() {
    val params = mutableMapOf<String, String?>()
    params["type"] = "yuantong"
    params["postid"] = "11111111111"
    scope.launch {
      val response = requestServer { APIInit.serverAPI.getWeather(params) }
      mITest?.let {
        if (response.success()) {
          it.showData(response.data)
        } else {
          it.showError(response.httpError?.msg)
        }
      }
    }
  }
  interface ITest {
    fun showData(data: List<LogisticsModel?>?)
    fun showError(s: String?)
  }
}