package com.code.runtime.utils

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import androidx.fragment.app.FragmentActivity
import com.billy.cc.core.component.CC
import com.code.runtime.contants.ComponentsContants
import com.code.runtime.contants.ComponentsContants.Action
import com.code.runtime.contants.ComponentsContants.Name
import com.code.runtime.contants.ShareContants.Plat
import com.code.runtime.dialog.FullDialog
import com.code.runtime.dialog.ShareDialogView
import com.code.runtime.dialog.ShareDialogView.Bean
import com.code.runtime.dialog.ShareDialogView.IShareDialogView
import java.io.Serializable

object ShareUtils {
  @JvmStatic fun share(
    context: Context?,
    shareBean: ShareBean?
  ) {
    if (shareBean == null || context !is FragmentActivity) {
      return
    }
    val fullDialog = FullDialog.newIntance(context.supportFragmentManager)
    val shareDialogView = ShareDialogView(context)
    shareDialogView.mIShareDialogView = object : IShareDialogView {
      override fun shareItemClick(bean: Bean?) {
        fullDialog.dismiss()
        if (bean == null) {
          return
        }
        var componentname: String? = null
        when (bean.plat) {
          Plat.WECHART, Plat.WECHARTZONE -> componentname = Name.Wechart
          Plat.QQ, Plat.QZONE -> componentname = Name.QQ
        }
        if (TextUtils.isEmpty(componentname)) {
          return
        }
        shareBean.plat = bean.plat
        CC.obtainBuilder(componentname)
            .setContext(context)
            .setActionName(Action.Share)
            .addParam(
                ComponentsContants.VIEW_DATA,
                shareBean
            )
            .build()
            .call()
      }
    }
    fullDialog.setContentView(shareDialogView)
        .setGravity(Gravity.BOTTOM)
        .setNeedBackGround(true)
        .showDidlog()
  }

  class ShareBean : Serializable {
    @JvmField var plat = 0
    @JvmField var type = 0
    @JvmField var title: String? = null
    @JvmField var content: String? = null
    @JvmField var imageUrl: String? = null
    @JvmField var clickUrl: String? = null
  }

  class Builder {
    private var plat = 0
    var type = 0
    private var title: String? = null
    private var content: String? = null
    private var imageUrl: String? = null
    private var clickUrl: String? = null
    fun setType(type: Int): Builder {
      this.type = type
      return this
    }

    fun setPlat(plat: Int): Builder {
      this.plat = plat
      return this
    }

    fun setTitle(title: String?): Builder {
      this.title = title
      return this
    }

    fun setContent(content: String?): Builder {
      this.content = content
      return this
    }

    fun setImageUrl(imageUrl: String?): Builder {
      this.imageUrl = imageUrl
      return this
    }

    fun setClickUrl(clickUrl: String?): Builder {
      this.clickUrl = clickUrl
      return this
    }

    fun build(): ShareBean {
      val shareBean = ShareBean()
      shareBean.plat = plat
      shareBean.type = type
      shareBean.clickUrl = clickUrl
      shareBean.content = content
      shareBean.imageUrl = imageUrl
      shareBean.title = title
      return shareBean
    }
  }

  interface IShareResult {
    fun shareResult(shareReult: ShareReult?)
  }

  class ShareReult(
    var result: Int,
    var plat: Int
  )
}