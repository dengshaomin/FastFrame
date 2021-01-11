package com.code.fastframe.baseview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.andview.refreshview.XRefreshView.SimpleXRefreshListener
import com.code.fastframe.R.layout
import com.code.fastframe.ciface.IBaseRecyclerViewCb
import com.code.fastframe.ciface.IHeadClick
import com.code.fastframe.ciface.IPageStateCb
import com.code.fastframe.recyclerview.CHeaderFooterAdapter
import kotlinx.android.synthetic.main.clist_view.view.page_state_view
import kotlinx.android.synthetic.main.clist_view.view.recyclerview
import kotlinx.android.synthetic.main.clist_view.view.xrefreshview

/**
 * Created by dengshaomin on 2017/12/4.
 */
open class BaseRecyclerView @JvmOverloads constructor(
  context: Context?,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : BaseViewLayout(context, attrs, defStyleAttr), IHeadClick, OnClickListener, IPageStateCb {
  lateinit var mIBaseRecyclerViewCb: IBaseRecyclerViewCb

  //刷新模式
  object Mode {
    const val START = 0
    const val END = 1
    const val BOTH = 2
    const val NONE = 3
  }

  var refreMode = 0
  var mAdapter: Adapter<*>? = null
  private var mCHeaderFooterAdapter: CHeaderFooterAdapter? = null
  var mCListViewFooter: BaseRcvFooterView? = null
  var refreshState = DEFAULT
  private var hasMoreData = true
//  fun setIBaseRecyclerViewCb(IBaseRecyclerViewCb: IBaseRecyclerViewCb?) {
//    mIBaseRecyclerViewCb = IBaseRecyclerViewCb
//  }

  //    public void setCListCallBackLister(IListCallBack CListCallBackLister) {
  //        mIBaseRecyclerViewCb = CListCallBackLister;
  //    }
  override fun setContentLayout(): Int {
    return layout.clist_view
  }

  //类似ios的回弹效果
  fun setSpringBackMode(mode: Int) {
    when (mode) {
      Mode.START -> {
        xrefreshview!!.enableRecyclerViewPullDown(true)
        xrefreshview!!.enableRecyclerViewPullUp(false)
      }
      Mode.END -> {
        xrefreshview!!.enableRecyclerViewPullUp(true)
        xrefreshview!!.enableRecyclerViewPullDown(false)
      }
      Mode.BOTH -> {
        xrefreshview!!.enableRecyclerViewPullUp(true)
        xrefreshview!!.enableRecyclerViewPullDown(true)
      }
      Mode.NONE -> {
        xrefreshview!!.enableRecyclerViewPullUp(false)
        xrefreshview!!.enableRecyclerViewPullDown(false)
      }
      else -> setSpringBackMode(Mode.NONE)
    }
  }

  override fun initView() {
    initXrefreshView()
  }

  private fun initXrefreshView() {
    //设置刷新完成以后，headerview固定的时间
    xrefreshview!!.setPinnedTime(300)
    xrefreshview!!.setMoveForHorizontal(true)
    xrefreshview!!.pullLoadEnable = true
    xrefreshview!!.setAutoLoadMore(false)
    setSpringBackMode(Mode.BOTH)
    //禁止手动上拉加载更过
    xrefreshview!!.enableReleaseToLoadMore(false)
    xrefreshview!!.enablePullUpWhenLoadCompleted(true)
    //设置静默加载时提前加载的item个数
//        xefreshView1.setPreLoadCount(4);
    //设置Recyclerview的滑动监听
    xrefreshview!!.setOnRecyclerViewScrollListener(object : OnScrollListener() {
      override fun onScrollStateChanged(
        recyclerView: RecyclerView,
        newState: Int
      ) {
//                super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
          if (isScrollBottom) {
            needLoadMore()
          }
        }
      }

      override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int
      ) {
//                super.onScrolled(recyclerView, dx, dy);
        if (dx > 0 && isScrollBottom) {
          needLoadMore()
        }
      }
    })
    xrefreshview!!.setXRefreshViewListener(object : SimpleXRefreshListener() {
      override fun onRefresh(isPullDown: Boolean) {
        refreshState = Mode.START
        if (mIBaseRecyclerViewCb != null) {
          mIBaseRecyclerViewCb!!.onRefresh(refreshState)
        } else {
          xrefreshview!!.stopRefresh()
        }
      }

      override fun onLoadMore(
        isSilence: Boolean,
        index: Int
      ) {
        needLoadMore()
      }
    })
  }

  protected open fun needLoadMore() {
    if (refreMode == Mode.START || refreMode == Mode.NONE || refreshState != DEFAULT) {
      return
    }
    refreshState = Mode.END
    if (mIBaseRecyclerViewCb != null) {
      mIBaseRecyclerViewCb!!.onRefresh(refreshState)
    }
  }

  fun addHeadView(view: View?) {
    if (mCHeaderFooterAdapter != null) {
      mCHeaderFooterAdapter!!.addHeaderItem(view)
    }
  }

  fun removeHeadView(view: View?) {
    if (mCHeaderFooterAdapter != null) {
      mCHeaderFooterAdapter!!.removeHeaderView(view)
    }
  }

  fun removeHeadView() {
    if (mCHeaderFooterAdapter != null) {
      mCHeaderFooterAdapter!!.removeHeaderItem()
    }
  }

  fun addFooterView(view: View?) {
    if (mCHeaderFooterAdapter != null) {
      mCHeaderFooterAdapter!!.addFooterItem(view)
    }
  }

  fun removeFooterView(view: View?) {
    if (mCHeaderFooterAdapter != null) {
      mCHeaderFooterAdapter!!.removeFootView(view)
    }
  }

  fun removeFooterView() {
    if (mCHeaderFooterAdapter != null) {
      mCHeaderFooterAdapter!!.removeFooterItem()
    }
  }

  //recyclerView.canScrollVertically(1) //是否滑动到最底部
  //recyclerView.canScrollVertically(-1)  //是否滑动到最顶部
  private val isScrollBottom: Boolean
    private get() {
      //recyclerView.canScrollVertically(1) //是否滑动到最底部
      //recyclerView.canScrollVertically(-1)  //是否滑动到最顶部
      val realyCount = mAdapter!!.itemCount
      if (recyclerview!!.layoutManager == null) {
        return true
      }
      return if (recyclerview!!.layoutManager is LinearLayoutManager) {
        realyCount - WhitchPositionAutoShowLoadMoreFootView <= (recyclerview!!.layoutManager as LinearLayoutManager)
            .findLastVisibleItemPosition()
      } else if (recyclerview!!.layoutManager is GridLayoutManager) {
        realyCount - WhitchPositionAutoShowLoadMoreFootView <= (recyclerview!!.layoutManager as GridLayoutManager)
            .findLastVisibleItemPosition()
      } else if (recyclerview!!.layoutManager is StaggeredGridLayoutManager) {
        val into = IntArray(0)
        (recyclerview!!.layoutManager as StaggeredGridLayoutManager?)!!.findLastVisibleItemPositions(
            into
        )
        if (into != null) {
          realyCount - WhitchPositionAutoShowLoadMoreFootView <= into[into.size - 1]
        } else {
          true
        }
      } else {
        true
      }
    }

  protected open fun initFootAndAdapter() {
    if (mCHeaderFooterAdapter == null) {
      mCHeaderFooterAdapter = CHeaderFooterAdapter(mAdapter, this)
    }
    if (mCListViewFooter == null) {
      mCListViewFooter = BaseRcvFooterView(context)
      mCHeaderFooterAdapter!!.addFooterItem(mCListViewFooter)
    }
    recyclerview!!.adapter = mCHeaderFooterAdapter
  }

  open fun setLayoutManager(layout: LayoutManager?) {
    if (recyclerview != null) {
      recyclerview!!.layoutManager = layout
    }
  }

  open fun addItemDecoration(decor: ItemDecoration?) {
    if (recyclerview != null) {
      recyclerview!!.addItemDecoration(decor!!)
    }
  }

  //    public void setCListActionInterface(CListRefreshInterface CListRefreshInterface) {
  //        mCListActionInterface = CListRefreshInterface;
  //    }
  fun setAdapter(adapter: Adapter<*>?) {
    mAdapter = adapter
    initFootAndAdapter()
  }

  fun setRefreshMode(mode: Int) {
    refreMode = mode
    if (mCListViewFooter != null) {
      mCListViewFooter!!.visibility =
        if (refreMode == Mode.END || refreMode == Mode.BOTH) VISIBLE else GONE
    }
    when (mode) {
      Mode.START -> {
        xrefreshview!!.pullRefreshEnable = true
        xrefreshview!!.pullLoadEnable = false
      }
      Mode.END -> {
        xrefreshview!!.pullRefreshEnable = false
        xrefreshview!!.pullLoadEnable = true
      }
      Mode.BOTH -> {
        xrefreshview!!.pullRefreshEnable = true
        xrefreshview!!.pullLoadEnable = false
      }
      Mode.NONE -> {
        xrefreshview!!.pullRefreshEnable = false
        xrefreshview!!.pullLoadEnable = false
      }
      else -> setRefreshMode(Mode.BOTH)
    }
  }

  fun refreshComplete(hasMoreData: Boolean) {
    this.hasMoreData = hasMoreData
    if (refreshState == Mode.START) {
      xrefreshview!!.stopRefresh()
    } else if (refreshState == Mode.END) {
      xrefreshview!!.stopLoadMore()
    }
    if (mCListViewFooter != null) {
      mCListViewFooter!!.setViewData(hasMoreData)
    }
    refreshState = DEFAULT
  }

  fun setLoadingViewState(pageState: Int) {
    page_state_view.setViewData(pageState)
  }

  override fun setViewData(data: Any?) {

  }

  override fun onClick(v: View) {}

  companion object {
    const val DEFAULT = -1
    const val WhitchPositionAutoShowLoadMoreFootView = 2 //2代表滑动倒数第几个触发自动加载
  }

  override fun pageStateViewClick(pageState: Int) {
  }

  override fun onHeadFootClickLister(
    view: View?,
    data: Any?,
    position: Int
  ) {
    if (mIBaseRecyclerViewCb != null) {
      mIBaseRecyclerViewCb!!.onHeadFootClickLister(view, data, position)
    }
  }
}