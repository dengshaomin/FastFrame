package com.code.runtime.dialog

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.code.fastframe.baseview.BaseViewLayout
import com.code.fastframe.utils.CompoundDrawableUtils
import com.code.runtime.R.drawable
import com.code.runtime.R.layout
import com.code.runtime.contants.ShareContants.Plat
import kotlinx.android.synthetic.main.view_share_dialog.view.cancel
import kotlinx.android.synthetic.main.view_share_dialog.view.recyclerview
import java.util.ArrayList

class ShareDialogView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BaseViewLayout(context, attrs, defStyleAttr) {
  var mIShareDialogView: IShareDialogView? = null

  private val mBeans: ArrayList<Bean?> = object : ArrayList<Bean?>() {
    init {
      add(Bean(Plat.WECHART, "微信好友", drawable.ic_share_wechat))
      add(Bean(Plat.WECHARTZONE, "微信朋友圈", drawable.ic_share_moments))
      add(Bean(Plat.QQ, "QQ好友", drawable.ic_share_qq))
      add(Bean(Plat.QZONE, "QQ空间", drawable.ic_share_qzone))
    }
  }

  override fun setViewData(data: Any?) {}
  override fun setContentLayout(): Int {
    return layout.view_share_dialog
  }

  override fun initView() {
    recyclerview.setLayoutManager(GridLayoutManager(context, 5))
    recyclerview.setAdapter(ShareAdapter())
    cancel.setOnClickListener(OnClickListener {
      if (mIShareDialogView != null) {
        mIShareDialogView!!.shareItemClick(null)
      }
    })
  }

  inner class ShareAdapter : Adapter<ViewHolder>() {
    override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
    ): ViewHolder {
      return object : ViewHolder(ShareItemView(context)) {}
    }

    override fun onBindViewHolder(
      holder: ViewHolder,
      position: Int
    ) {
      val shareItemView = holder!!.itemView as ShareItemView
      shareItemView.setViewData(mBeans[position])
      shareItemView.setOnClickListener {
        if (mIShareDialogView != null) {
          mIShareDialogView!!.shareItemClick(mBeans[position])
        }
      }
    }

    override fun getItemCount(): Int {
      return mBeans.size
    }
  }

  inner class Bean(
    var plat: Int = Plat.WECHART,
    var name: String,
    var resource: Int
  ) {
  }

  class ShareItemView : BaseViewLayout {
    private var text: TextView? = null

    constructor(context: Context?) : super(context) {}
    constructor(
      context: Context?,
      attrs: AttributeSet?
    ) : super(context, attrs) {
    }

    constructor(
      context: Context?,
      attrs: AttributeSet?,
      defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    override fun setViewData(data: Any?) {
      val bean = data as Bean?
      CompoundDrawableUtils.setDrawalbes(context, text, bean!!.resource, Gravity.TOP)
      text!!.text = bean.name
    }

    override fun setContentLayout(): Int {
      return layout.share_item_view
    }

    override fun initView() {
    }
  }

  interface IShareDialogView {
    fun shareItemClick(bean: Bean?)
  }
}