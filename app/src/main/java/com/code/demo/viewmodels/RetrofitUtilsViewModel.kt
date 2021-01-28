package com.code.demo.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.demo.apis.APIInit
import com.code.demo.demo.model.LogisticsModel
import com.code.fastframe.retrofit.HttpUtils.requestServer
import kotlinx.coroutines.launch

class RetrofitUtilsViewModel @ViewModelInject constructor() :
    ViewModel() {
  val liveData = MutableLiveData<List<LogisticsModel>>()
  fun requestData() {
    viewModelScope.launch {
      val params = mutableMapOf<String, String?>()
      params["type"] = "yuantong"
      params["postid"] = "11111111111"
      val response = requestServer { APIInit.serverAPI.getWeather(params) }
      liveData.postValue(response.data)
    }
  }
}