package com.code.demo.demo

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.code.demo.R.layout
import com.code.fastframe.baseactivity.BaseTitleActivity
import com.code.fastframe.baseview.BaseRecyclerView.Mode
import com.code.fastframe.baseview.PageStateView.State
import com.code.fastframe.ciface.IBaseRecyclerViewCb
import com.code.fastframe.utils.CollectionUtils
import com.code.fastframe.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_clist_view.baerecycler_view
import java.util.ArrayList

class BaseRecyclerSimpleActivity : BaseTitleActivity(), IBaseRecyclerViewCb {
  private var datas: ArrayList<String> = mutableListOf<String>() as ArrayList<String>
  private var mTestAdapter: TestAdapter? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun setContentLayout(): Int {
    return layout.activity_clist_view
  }

  override fun initView() {
    baerecycler_view.setRefreshMode(Mode.BOTH)
    baerecycler_view.setSpringBackMode(Mode.BOTH)
    baerecycler_view.setLayoutManager(LinearLayoutManager(this))
    baerecycler_view.mIBaseRecyclerViewCb = this
    mTestAdapter = TestAdapter(this, datas)
    baerecycler_view.setAdapter(mTestAdapter)
  }

  override fun initData() {
    onRefresh(Mode.START)
  }

  override fun setTitleText(): String {
    return this.javaClass.simpleName
  }

  override fun onHeadFootClickLister(
    view: View?,
    data: Any?,
    position: Int
  ) {
    ToastUtils.showToast(position.toString() + "")
  }

  override fun onRefresh(state: Int) {
    Handler().postDelayed({
      val netdata = bornData(state)
      if (!CollectionUtils.isEmpty(netdata)) {
        if (datas == null) {
          datas = ArrayList()
        }
        if (state == Mode.START) {
          datas!!.clear()
        }
        datas!!.addAll(netdata)
      }
      mTestAdapter!!.updateData(datas)
      baerecycler_view!!.refreshComplete(true)
      updatePageStateView()
    }, 1000)
  }

  private fun updatePageStateView() {
    if (CollectionUtils.isNull(datas)) {
      baerecycler_view!!.setLoadingViewState(
          if (baerecycler_view!!.refreshState == Mode.START) State.ERROR else State.SUCCESS
      )
    } else if (CollectionUtils.isEmpty(datas)) {
      baerecycler_view!!.setLoadingViewState(
          if (baerecycler_view!!.refreshState == Mode.START) State.EMPTY else State.SUCCESS
      )
    } else {
      baerecycler_view!!.setLoadingViewState(State.SUCCESS)
    }
  }

  private fun bornData(currentState: Int): ArrayList<String> {
    var length = 0
    if (currentState != Mode.START) {
      length = if (CollectionUtils.isEmpty(datas)) 0 else datas!!.size
    }
    val strs: ArrayList<String> = ArrayList()
    for (i in length until length + 10) {
      strs.add(i.toString() + "")
    }
    return strs
  }

  inner class TestAdapter(
    private val mContext: Context,
    private var datas: ArrayList<String>
  ) : Adapter<ViewHolder>() {
    fun updateData(datas: ArrayList<String>) {
      this.datas = datas
      notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
    ): ViewHolder {
      return object : ViewHolder(TestItem(mContext)) {}
    }

    override fun onBindViewHolder(
      holder: ViewHolder,
      position: Int
    ) {
      val testItem = holder.itemView as TestItem
      testItem.setViewData(datas!![position])
    }

    override fun getItemCount(): Int {
      return if (datas == null) 0 else datas!!.size
    }
  }

  override fun needImmerse(): Boolean {
    return true
  }
}