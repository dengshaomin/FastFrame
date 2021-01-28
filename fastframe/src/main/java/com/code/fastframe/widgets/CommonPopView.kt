package com.code.fastframe.widgets

import android.content.Context
import com.code.fastframe.R
import com.lxj.xpopup.impl.FullScreenPopupView
import kotlinx.android.synthetic.main.view_common_pop.view.*

class CommonPopView(
  context: Context,
  var iPopView: IPopView?,
  var contentTx: String?,
  var cancelTx: String?,
  var sureTx: String?,
  var cancelColor: Int,
  var sureColor: Int,
) :
    FullScreenPopupView(context) {
  companion object {

  }

  init {
    cancelTx?.let {
      cancel.text = cancelTx
    }
    sureTx?.let {
      sure.text = sureTx
    }
    contentTx?.let {
      content.text = contentTx
    }
    if (cancelColor != 0) {
      cancel.setTextColor(cancelColor)
    }
    if (sureColor != 0) {
      sure.setTextColor(sureColor)
    }
  }

  override fun initPopupContent() {
    super.initPopupContent()
    sure.setOnClickListener {
      dismiss()
      iPopView?.sure()

    }
    cancel.setOnClickListener {
      dismiss()
      iPopView?.cancel()
    }
  }

  override fun getImplLayoutId(): Int {
    return R.layout.view_common_pop
  }

  class Builder(var context: Context) {
    private var cancel_tx: String? = null
    private var cancel_color = 0
    private var sure_tx: String? = null
    private var sure_color = 0
    private var content_tx: String? = null
    private var iPopView: IPopView? = null
    fun cancelTx(tx: String): Builder {
      this.cancel_tx = tx
      return this
    }

    fun sureTx(tx: String): Builder {
      this.sure_tx = tx
      return this
    }

    fun listener(iPopView: IPopView?): Builder {
      this.iPopView = iPopView
      return this
    }

    fun cancelColor(color: Int): Builder {
      this.cancel_color = color
      return this
    }

    fun sureColor(color: Int): Builder {
      this.sure_color = color
      return this
    }

    fun contentStr(content: String?): Builder {
      this.content_tx = content
      return this
    }

    fun build(): CommonPopView {
      var commonPopView = CommonPopView(
          context,
          iPopView,
          content_tx,
          cancel_tx,
          sure_tx,
          cancel_color,
          sure_color
      )
      return commonPopView
    }
  }

  interface IPopView {
    fun cancel()
    fun sure()
  }
}