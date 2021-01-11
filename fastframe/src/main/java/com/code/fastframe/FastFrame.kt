package com.code.fastframe

import android.content.Context
import android.content.IntentFilter
import android.graphics.Bitmap.Config.ARGB_8888
import android.net.ConnectivityManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import com.code.fastframe.net.NetworkStatusReceiver
import com.code.fastframe.retrofit.HttpUtils
import com.code.fastframe.utils.SharedPreferencesUtils
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig
import com.facebook.imagepipeline.image.ImmutableQualityInfo
import com.facebook.imagepipeline.image.QualityInfo
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import okhttp3.OkHttpClient
import java.util.HashSet

/**
 * Created by dengshaomin on 2017/12/6.
 */
class FastFrame {
  private var mNetworkReceiver: NetworkStatusReceiver? = null
  @Synchronized fun init(context: Context) {
    if (mApplicationContext != null) {
      return
    }
    mApplicationContext = context.applicationContext
    HttpUtils.init(mApplicationContext)
    initFresco()
  }

  private fun initFresco() {
    val enableDownSample = VERSION.SDK_INT != VERSION_CODES.KITKAT
    val pjpegConfig: ProgressiveJpegConfig = object : ProgressiveJpegConfig {
      override fun getNextScanNumberToDecode(scanNumber: Int): Int {
        return scanNumber + 2
      }

      override fun getQualityInfo(scanNumber: Int): QualityInfo {
        val isGoodEnough = scanNumber >= 5
        return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false)
      }
    }
    val listeners: MutableSet<RequestListener> = HashSet()
    listeners.add(RequestLoggingListener())
    val imagePiplineConfig = OkHttpImagePipelineConfigFactory
        .newBuilder(mApplicationContext, OkHttpClient())
        .setRequestListeners(listeners)
        .setBitmapsConfig(ARGB_8888)
        .setProgressiveJpegConfig(pjpegConfig)
        .setDownsampleEnabled(enableDownSample)
        .build()
    Fresco.initialize(mApplicationContext, imagePiplineConfig)
  }

  private fun registerNetStatusReceiver() {
    if (mNetworkReceiver == null) {
      mNetworkReceiver = NetworkStatusReceiver()
      mApplicationContext!!.registerReceiver(
          mNetworkReceiver,
          IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
      )
    }
  }

  private fun unRegisterNetStatusReceiver() {
    if (mNetworkReceiver != null) {
      mNetworkReceiver = NetworkStatusReceiver()
      mApplicationContext!!.unregisterReceiver(mNetworkReceiver)
    }
  }

  fun onDestory() {
    SharedPreferencesUtils.getInstance().commit()
    unRegisterNetStatusReceiver()
  }

  companion object {
    @JvmField var mApplicationContext: Context? = null

    @Volatile
    private var fastFrame: FastFrame? = null
    @JvmStatic val instance: FastFrame?
      get() {
        if (fastFrame == null) {
          synchronized(FastFrame::class.java) {
            if (fastFrame == null) {
              fastFrame = FastFrame()
            }
          }
        }
        return fastFrame
      }
  }
}