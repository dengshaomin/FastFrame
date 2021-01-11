package com.code.demo.demo

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import android.widget.RadioGroup
import com.alibaba.fastjson.JSON
import com.code.demo.R.id
import com.code.demo.R.layout
import com.code.fastframe.baseactivity.BaseTitleActivity
import com.code.fastframe.eventbus.GlobalEvent
import com.code.fastframe.utils.RealPathFromUriUtils
import com.code.fastframe.utils.ToastUtils
import com.code.runtime.contants.ComponentsContants.Action
import com.code.runtime.contants.GlobalEventContants
import com.code.runtime.contants.Result
import com.code.runtime.contants.ShareContants.Plat
import com.code.runtime.contants.ShareContants.Type
import com.code.runtime.factory.UserInfoFactory
import com.code.runtime.models.events.ThirdPlatEventBean
import com.code.runtime.utils.LoginUtils
import com.code.runtime.utils.ShareUtils.Builder
import com.code.runtime.utils.ShareUtils.share
import kotlinx.android.synthetic.main.activity_share.*
import java.util.ArrayList

class ShareActivity : BaseTitleActivity(), OnClickListener {
  var shareLocalPath: String? = null
  override fun setContentLayout(): Int {
    return layout.activity_share
  }

  override fun initView() {
    wechartlogin.setOnClickListener(this)
    qqlogin.setOnClickListener(this)
    share.setOnClickListener(this)
    share_type.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
      if (checkedId == id.share_type_image) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)
      }
    })
  }

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK) {
      //获取选中文件的定位符
      val uri = data!!.data
      if (uri != null) {
        shareLocalPath = RealPathFromUriUtils.getRealPathFromUri(this, uri)
      }
    }
  }

  override fun setTitleText(): String {
    return this.javaClass.simpleName
  }

  override fun onClick(v: View) {
    if (v === wechartlogin) {
      LoginUtils.login(this, Plat.WECHART)
    } else if (v === qqlogin) {
      LoginUtils.login(this, Plat.QQ)
    } else if (v === share) {
      if (share_type!!.checkedRadioButtonId == id.share_type_text_image) {
        share(
            this, Builder().setType(Type.IMAGE_TEXT).setTitle("123").setContent("456").setImageUrl(
            "https"
                + "://ss3"
                + ".bdstatic"
                + ".com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=487628588,2307382441&fm=26&gp=0.jpg"
        ).setClickUrl("https://www.baidu.com").build()
        )
      } else if (share_type!!.checkedRadioButtonId == id.share_type_image) {
        if (TextUtils.isEmpty(shareLocalPath)) {
          ToastUtils.showToast("选择本地图片")
          return
        }
        shareLocalPath = shareLocalPath!!.replace("content://", "")
        share(
            this, Builder().setType(Type.IMAGE)
            .setImageUrl(shareLocalPath).build()
        )
      }
    }
  }

  override fun needGlobalEvent(): Boolean {
    return true
  }

  override fun regeistEvent(): ArrayList<Int> {
    return object : ArrayList<Int>() {
      init {
        add(GlobalEventContants.THIRD_PLAT)
      }
    }
  }

  override fun eventComming(globalMsg: GlobalEvent) {
    super.eventComming(globalMsg)
    if (globalMsg.msgId == GlobalEventContants.THIRD_PLAT) {
      val thirdPlatEventBean = globalMsg.data as ThirdPlatEventBean
      if (thirdPlatEventBean.plat == Plat.QQ) {
        if (TextUtils.equals(thirdPlatEventBean.type, Action.Login)) {
          if (thirdPlatEventBean.result == Result.CALCEL) {
            ToastUtils.showToast("用户取消登录")
          } else if (thirdPlatEventBean.result == Result.FAIL) {
            ToastUtils.showToast("登录失败")
          } else {
            ToastUtils.showToast("登录成功")
            qq_info!!.text = JSON.toJSONString(UserInfoFactory.getInstance().mQQUserInfoBean)
          }
        } else if (TextUtils.equals(thirdPlatEventBean.type, Action.Share)) {
          if (thirdPlatEventBean.result == Result.CALCEL) {
            ToastUtils.showToast("用户取消分享")
          } else if (thirdPlatEventBean.result == Result.FAIL) {
            ToastUtils.showToast("分享失败")
          } else {
            ToastUtils.showToast("分享成功")
            qq_info!!.text = JSON.toJSONString(UserInfoFactory.getInstance().mQQUserInfoBean)
          }
        }
      }
    }
  }
}