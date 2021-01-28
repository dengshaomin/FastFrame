package com.code.demo.apis

import com.code.demo.BuildConfig
import com.code.fastframe.retrofit.ApiFactory

class APIInit {
  companion object {
    lateinit var serverAPI: ServerAPI

    @Synchronized
    fun initAPI() {
      serverAPI = ApiFactory.createServerApi(ServerAPI::class.java, BuildConfig.SERVER_URL)
    }
  }
}