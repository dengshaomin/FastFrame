package com.code.demo.apis

import com.code.demo.demo.model.LogisticsModel
import com.code.fastframe.retrofit.models.ServerModel
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ServerAPI {
  @GET("/query")
  suspend fun getWeather(@QueryMap params: MutableMap<String, String?>?): ServerModel<MutableList<LogisticsModel>>
}