package com.code.fastframe.baseview

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import com.code.fastframe.R.layout
import com.code.fastframe.ciface.IPageStateCb
import com.code.fastframe.ciface.IPageStateView
import com.code.fastframe.utils.NetWorkUtils
import kotlinx.android.synthetic.main.state_base_view.view.vstub_empty
import kotlinx.android.synthetic.main.state_base_view.view.vstub_error
import kotlinx.android.synthetic.main.state_base_view.view.vstub_net
import kotlinx.android.synthetic.main.state_empty_view.view.empty_image
import kotlinx.android.synthetic.main.state_empty_view.view.empty_lay
import kotlinx.android.synthetic.main.state_empty_view.view.empty_tip
import kotlinx.android.synthetic.main.state_error_view.view.error_image
import kotlinx.android.synthetic.main.state_error_view.view.error_lay
import kotlinx.android.synthetic.main.state_error_view.view.error_tip
import kotlinx.android.synthetic.main.state_loading_view.view.loading_lay
import kotlinx.android.synthetic.main.state_loading_view.view.loading_tip
import kotlinx.android.synthetic.main.state_net_view.view.net_lay
import kotlinx.android.synthetic.main.state_net_view.view.net_tip

/**
 * Created by dengshaomin on 2017/12/8.
 */
class PageStateView : BaseViewLayout, OnClickListener, IPageStateView {
  object State {
    const val EMPTY = 0
    const val ERROR = 1
    const val SUCCESS = 2
    const val LOADING = 3
  }

  var currentState = State.LOADING
    set(value) {
      setViewData(currentState)
    }
  private var emptyResource = 0
  private var errorResource = 0
  private var netResource = 0
  private var errorTip = ""
  private var emptyTip = ""
  private var loadingTip = ""
  private var netTip = ""

  var iPageStateCb: IPageStateCb? = null

  constructor(
    context: Context?,
    iPageStateCb: IPageStateCb?
  ) : super(
      context!!
  ) {
    this.iPageStateCb = iPageStateCb
  }

  constructor(
    context: Context?,
    attrs: AttributeSet?
  ) : super(
      context!!, attrs
  ) {
  }

  constructor(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int
  ) : super(
      context!!, attrs, defStyleAttr
  ) {
  }

  override fun setContentLayout(): Int {
    return layout.state_base_view
  }

  override fun initView() {
    initLoadingLay()
  }

  private fun initLoadingLay() {

  }

  private fun initNetLay() {
    if (vstub_net!!.parent != null) {
      val view = vstub_net!!.inflate()
      net_lay.setOnClickListener(this)
    }
  }

  private fun initErrorLay() {
    if (vstub_error!!.parent != null) {
      val view = vstub_error!!.inflate()
      error_tip.setOnClickListener(this)
    }
  }

  private fun initEmptyLay() {
    if (vstub_empty!!.parent != null) {
      val view = vstub_empty!!.inflate()
      empty_tip.setOnClickListener(this)
    }
  }

  override fun initData() {}
  override fun setViewData(data: Any?) {
//    currentState = data as Int
    visibility =
      if (currentState == State.SUCCESS) GONE else VISIBLE
    if (State.EMPTY == currentState) {
      initEmptyLay()
      empty_lay?.visibility = VISIBLE
      if (loading_lay != null) {
        loading_lay!!.visibility = GONE
      }
      if (net_lay != null) {
        net_lay!!.visibility = GONE
      }
      error_lay?.visibility = GONE
    } else if (State.ERROR == currentState) {
      if (NetWorkUtils.connected(context)) {
        initErrorLay()
        if (net_lay != null) {
          net_lay!!.visibility = GONE
        }
        if (error_lay != null) {
          error_lay!!.visibility = VISIBLE
        }
      } else {
        initNetLay()
        if (net_lay != null) {
          net_lay!!.visibility = VISIBLE
        }
        if (error_lay != null) {
          error_lay!!.visibility = GONE
        }
      }
      if (loading_lay != null) {
        loading_lay!!.visibility = GONE
      }
      if (empty_lay != null) {
        empty_lay!!.visibility = GONE
      }
    } else if (State.SUCCESS == currentState) {
      visibility = GONE
    } else if (State.LOADING == currentState) {
      initLoadingLay()
      if (loading_lay != null) {
        loading_lay!!.visibility = VISIBLE
      }
      if (empty_lay != null) {
        empty_lay!!.visibility = GONE
      }
      if (net_lay != null) {
        net_lay!!.visibility = GONE
      }
      if (error_lay != null) {
        error_lay!!.visibility = GONE
      }
    } else {
      visibility = GONE
    }
  }

  override fun setEmptyResource(emptyResource: Int) {
    this.emptyResource = emptyResource
    if (emptyResource > 0) {
      empty_image?.setBackgroundResource(this.emptyResource)
    }
  }

  override fun setErrorResource(errorResource: Int) {
    this.errorResource = errorResource
    if (errorResource > 0) {
      error_image?.setBackgroundResource(this.errorResource)
    }
  }

  override fun setNetResource(netResource: Int) {
    this.netResource = netResource
    if (netResource > 0) {
      empty_image!!.setBackgroundResource(this.netResource)
    }
  }

  override fun setErrorTip(errorTip: String) {
    this.errorTip = errorTip
    if (!TextUtils.isEmpty(errorTip)) {
      error_tip!!.text = this.errorTip
    }
  }

  override fun setEmptyTip(emptyTip: String) {
    this.emptyTip = emptyTip
    if (!TextUtils.isEmpty(emptyTip)) {
      empty_tip!!.text = this.emptyTip
    }
  }

  override fun setLoadingTip(loadingTip: String) {
    this.loadingTip = loadingTip
    if (!TextUtils.isEmpty(loadingTip)) {
      loading_tip?.text = this.loadingTip
    }
  }

  override fun setNetTip(netTip: String) {
    this.netTip = netTip
    if (!TextUtils.isEmpty(netTip)) {
      net_tip?.text = this.netTip
    }
  }

  override fun onClick(v: View) {
    if (iPageStateCb != null) {
      iPageStateCb!!.pageStateViewClick(currentState)
    }
  }
}