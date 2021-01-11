package com.code.demo

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.multidex.MultiDex
import cn.jpush.android.api.JPushInterface
import com.code.demo.activity.LoadMultidexActivity
import com.code.demo.apis.APIInit
import com.code.fastframe.utils.MultidexUtils
import com.code.fastframe.utils.ProcessUtil
import com.tencent.android.tpush.XGIOperateCallback
import com.tencent.android.tpush.XGPushConfig
import com.tencent.android.tpush.XGPushManager
import com.tencent.bugly.Bugly
import com.tencent.bugly.crashreport.CrashReport
import java.io.File

/**
 * Created by dengshaomin on 2017/12/6.
 */
class MineApplication : Application() {
  private var multidexFlagFile: File? = null
  private var multidexInstalledFlagFile: File? = null
  private val multidex_flag_file_name = "multidex_flag.tmp"
  private val multidex_flag_installed_file_name = "multidex_installed_flag.tmp"
  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    multidexInstalledFlagFile = File(base.cacheDir.absoluteFile, multidex_flag_installed_file_name)
    //主进程并且vm不支持多dex的情况下才使用 Multidex
    if (!multidexInstalledFlagFile!!.exists() && ProcessUtil.isMainAppProcess(base)
        && MultidexUtils.supportMultidex()
    ) {
      loadMultiDex(base)
    } else {
      MultiDex.install(base)
    }
  }

  private fun loadMultiDex(context: Context) {
    createMultidexFlagFile(context)
    //启动多进程去加载MultiDex
    val intent = Intent(context, LoadMultidexActivity::class.java)
    intent.putExtra(LoadMultidexActivity.MULTIDEX_FLAG_FILE, multidexFlagFile!!.path)
    intent.putExtra(
        LoadMultidexActivity.MULTIDEX_INSTALLED_FLAG_FILE,
        multidexInstalledFlagFile!!.path
    )
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
    //阻塞当前线程，在Application中是不会出现anr的
    while (multidexFlagFile!!.exists()) {
      try {
        Thread.sleep(10)
      } catch (e: InterruptedException) {
        e.printStackTrace()
      }
    }
    MultiDex.install(context)
  }

  private fun createMultidexFlagFile(context: Context) {
    try {
      multidexFlagFile = File(context.cacheDir.absolutePath, multidex_flag_file_name)
      if (!multidexFlagFile!!.exists()) {
        multidexFlagFile!!.createNewFile()
      }
    } catch (th: Throwable) {
      th.printStackTrace()
    }
  }

  override fun onCreate() {
    super.onCreate()
    if (ProcessUtil.isMainAppProcess(applicationContext)) {
      initXinGe()
      initJPush()
      initBugly()
      initApis()
    }
  }

  private fun initXinGe() {
    XGPushConfig.enableDebug(this, BuildConfig.DEBUG)
    XGPushManager.registerPush(this, object : XGIOperateCallback {
      override fun onSuccess(
        data: Any,
        flag: Int
      ) {
        //token在设备卸载重装的时候有可能会变
        Log.e("TPush", "注册成功，设备token为：$data")
      }

      override fun onFail(
        data: Any,
        errCode: Int,
        msg: String
      ) {
        Log.d("TPush", "注册失败，错误码：$errCode,错误信息：$msg")
      }
    })
    XGPushManager.setTag(this, "XINGE")
  }

  private fun initJPush() {
    JPushInterface.setDebugMode(BuildConfig.DEBUG)
    JPushInterface.init(this)
  }

  private fun initBugly() {
    if (!BuildConfig.DEBUG) {
      CrashReport.initCrashReport(
          applicationContext, BuildConfig.BUGLY_APP_ID,
          BuildConfig.DEBUG
      )
    }
    if (BuildConfig.BUGLY_APP_UPDATE) {
      Bugly.init(applicationContext, BuildConfig.BUGLY_APP_ID, BuildConfig.DEBUG)
    }
  }

  fun initApis() {
    APIInit.initAPI()
  }
}