package com.code.demo.apis

import com.code.fastframe.retrofit.ApiFactory

class APIInit {
  companion object {
    lateinit var serverAPI: ServerAPI

    @Synchronized
    fun initAPI() {
      serverAPI = ApiFactory.createServerApi(ServerAPI::class.java,"http://www.kuaidi100.com")
    }
  }
}