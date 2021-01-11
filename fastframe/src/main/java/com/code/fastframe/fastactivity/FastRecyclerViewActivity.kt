package com.code.fastframe.fastactivity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.code.fastframe.R
import com.code.fastframe.R.layout
import com.code.fastframe.baseactivity.BaseTitleActivity
import com.code.fastframe.baseview.BaseRecyclerView.Mode
import com.code.fastframe.baseview.PageStateView.State
import com.code.fastframe.ciface.IFastRecyclerViewCb
import com.code.fastframe.recyclerview.FastRecyclerView
import com.code.fastframe.utils.CollectionUtils
import java.util.ArrayList

/**
 * 快速创建列表页面
 */
abstract class FastRecyclerViewActivity : BaseTitleActivity(), IFastRecyclerViewCb {
  lateinit var fast_rcv_view: FastRecyclerView
  override fun setContentLayout(): Int {
    return layout.activity_fast_recyclerview
  }

  override fun initView() {
    fast_rcv_view = findViewById(R.id.fast_rcv_view)
    fast_rcv_view.setRefreshMode(Mode.BOTH)
    fast_rcv_view.setSpringBackMode(Mode.BOTH)
    fast_rcv_view.setLayoutManager(LinearLayoutManager(this))
    fast_rcv_view.mIBaseRecyclerViewCb = this
  }

  override fun onHeadFootClickLister(
    view: View?,
    data: Any?,
    position: Int
  ) {
  }

  fun updateData(datas: ArrayList<Any>?) {
    fast_rcv_view?.updateData(datas)
    updatePageStateView()
  }

  private fun updatePageStateView() {
    if (CollectionUtils.isNull(fast_rcv_view!!.getDatas())) {
      fast_rcv_view!!.setLoadingViewState(
          if (fast_rcv_view!!.refreshState == Mode.START) State.ERROR else State.SUCCESS
      )
    } else if (CollectionUtils.isEmpty(fast_rcv_view!!.getDatas())) {
      fast_rcv_view!!.setLoadingViewState(
          if (fast_rcv_view!!.refreshState == Mode.START) State.EMPTY else State.SUCCESS
      )
    } else {
      fast_rcv_view!!.setLoadingViewState(State.SUCCESS)
    }
  }
}