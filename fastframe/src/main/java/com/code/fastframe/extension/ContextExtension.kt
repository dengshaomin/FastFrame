package com.code.fastframe.extension

import android.content.Context
import com.code.fastframe.R
import com.code.fastframe.widgets.CommonPopView
import com.code.fastframe.widgets.CommonPopView.IPopView
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupAnimation

var loadingPopView: BasePopupView? = null
fun dimissLoadingDialog() {
  loadingPopView?.dismiss()
}

fun Context.showLoadingDialog() {
  if (loadingPopView == null || loadingPopView!!.popupInfo == null) {
    loadingPopView = XPopup.Builder(this)
        .dismissOnBackPressed(true)
//                .enableShowWhenAppBackground(true)
        .isDestroyOnDismiss(false) //对于只使用一次的弹窗，推荐设置这个
        .asLoading("", R.layout.view_loading_dialog)
  }
  if (!loadingPopView!!.isShow && loadingPopView!!.popupInfo != null) {
    loadingPopView?.show()
  }
}

fun Context.showTipDialog(iPopView: IPopView): BasePopupView {
  var basePopupView = XPopup.Builder(this).isDestroyOnDismiss(true)
      .popupAnimation(PopupAnimation.NoAnimation)
      .asCustom(
          CommonPopView.Builder(this)
//              .contentStr("取消关注${beatMakerInfoData.stageName}?")
//              .cancelTx("取消关注")
//              .cancelColor(this@MineAttentionActivity.resources.getColor(R.color.white_20))
//              .sureTx("继续关注")
//              .sureColor(this@MineAttentionActivity.resources.getColor(R.color.color_face7d))
              .listener(iPopView).build()
      )
  basePopupView.show()
  return basePopupView
}




