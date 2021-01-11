package com.code.demo.demo

import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.code.demo.R
import com.code.fastframe.baseview.BaseRecyclerView.Mode
import com.code.fastframe.fastactivity.FastRecyclerViewActivity
import com.code.fastframe.utils.CollectionUtils
import java.util.ArrayList

class FastRecycleSimpleActivity : FastRecyclerViewActivity() {

  override fun setTitleText(): String {
    return this.javaClass.simpleName
  }

  override fun getNetData() {
    onRefresh(Mode.START)
  }

  override fun onRefresh(currentState: Int) {
    Log.e(this.javaClass.simpleName, currentState.toString() + "")
    Handler().postDelayed({ //                datas.clear();
      updateData(bornData(currentState))
    }, 1000)
  }

  private fun bornData(currentState: Int): ArrayList<Any> {
    var length = 0
    if (currentState != Mode.START) {
      length =
        if (CollectionUtils.isEmpty(fast_rcv_view.getDatas())) 0 else fast_rcv_view.getDatas()
            ?.size!!
    }
    val strs = ArrayList<Any>()
    for (i in length until length + 10) {
      strs.add(i.toString() + "")
    }
    return strs
  }

  override fun onItemClickLister(
    view: View,
    data: Any,
    position: Int
  ) {
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder {
    return object : ViewHolder(TestItem(this)) {}
  }

  override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    val testItem = holder.itemView as TestItem
    testItem.setViewData(fast_rcv_view.getDatas()?.get(position))
  }
}