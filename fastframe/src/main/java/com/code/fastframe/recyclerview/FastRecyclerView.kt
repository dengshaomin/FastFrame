package com.code.fastframe.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.code.fastframe.baseview.BaseRecyclerView
import com.code.fastframe.ciface.IFastRecyclerViewCb
import com.code.fastframe.utils.CollectionUtils
import kotlinx.android.synthetic.main.clist_view.view.recyclerview
import java.util.ArrayList

/**
 * Created by dengshaomin on 2017/12/4.
 */
class FastRecyclerView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BaseRecyclerView(context, attrs, defStyleAttr) {
  private val pageSize = 10
  var pageIndex = 1
  private var datas = mutableListOf<Any>()

  init {

  }

  fun getDatas(): List<Any>? {
    return datas
  }

  override fun initView() {
    super.initView()
    initFootAndAdapter()
  }

  fun updateData(datas: ArrayList<Any>?) {
    if (CollectionUtils.isEmpty(datas)) {
      refreshComplete(false)
      return
    }
    if (refreshState == Mode.START) {
      this.datas.clear()
    }
    datas?.let { this.datas.addAll(it) }
    refreshComplete(mAdapter!!.itemCount % pageSize == 0)
    mAdapter!!.notifyDataSetChanged()
  }

  override fun initFootAndAdapter() {
    if (mAdapter == null) {
      mAdapter = object : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(
          parent: ViewGroup,
          viewType: Int
        ): ViewHolder {
          return (mIBaseRecyclerViewCb as IFastRecyclerViewCb).onCreateViewHolder(
              parent, viewType
          )
        }

        override fun onBindViewHolder(
          holder: ViewHolder,
          position: Int
        ) {
          if (mIBaseRecyclerViewCb != null) {
            (mIBaseRecyclerViewCb as IFastRecyclerViewCb).onBindViewHolder(holder, position)
            if (holder != null && holder.itemView != null && datas != null && 0 <= position && position < datas.size) {
              holder.itemView.setOnClickListener {
                (mIBaseRecyclerViewCb as IFastRecyclerViewCb).onItemClickLister(
                    holder.itemView, datas.get(position), position
                )
              }
            }
          }
        }

        override fun getItemCount(): Int {
          return if (datas == null) 0 else datas.size
        }

      }
    }
    super.initFootAndAdapter()
  }

  override fun setLayoutManager(layout: LayoutManager?) {
    if (recyclerview != null) {
      recyclerview!!.layoutManager = layout
    }
  }

  override fun addItemDecoration(decor: ItemDecoration?) {
    if (recyclerview != null) {
      recyclerview!!.addItemDecoration(decor!!)
    }
  }

  //    public void setCListActionInterface(CListRefreshInterface CListRefreshInterface) {
  //        mCListActionInterface = CListRefreshInterface;
  //    }
  //    public void setAdapter(Adapter adapter) {
  //        this.mAdapter = adapter;
  //        if (mCHeaderFooterAdapter == null) {
  //            mCHeaderFooterAdapter = new CHeaderFooterAdapter(adapter, this);
  //        } else {
  //            mCHeaderFooterAdapter.setAdapter(adapter);
  //        }
  //        recyclerview.setAdapter(mCHeaderFooterAdapter);
  //    }
  override fun needLoadMore() {
    if (refreMode == Mode.START || refreMode == Mode.NONE || refreshState != DEFAULT) {
      return
    }
    if (mAdapter!!.itemCount == 0 || mAdapter!!.itemCount % pageSize != 0) {
      return
    }
    if (mCListViewFooter != null) {
      mCListViewFooter!!.setViewData(hasMoreData())
    }
    pageIndex = mAdapter!!.itemCount / pageSize + 1
    refreshState = Mode.END
    if (mIBaseRecyclerViewCb != null) {
      mIBaseRecyclerViewCb.onRefresh(refreshState)
    }
  }

  private fun hasMoreData(): Boolean {
    if (mAdapter == null) {
      return false
    }
    return if (mAdapter!!.itemCount == 0 || mAdapter!!.itemCount % pageSize != 0) false else true
  } //    public void refreshComplete() {
  //        if (refreshState == Mode.START) {
  //            mXrefreshview.stopRefresh();
  //        } else if (refreshState == Mode.END) {
  //            mXrefreshview.stopLoadMore();
  //        }
  //        if (mCListViewFooter != null) {
  //            mCListViewFooter.setViewData(hasMoreData());
  //        }
  //        refreshState = DEFAULT;
  //    }
}