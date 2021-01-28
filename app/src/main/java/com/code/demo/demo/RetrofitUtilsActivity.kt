package com.code.demo.demo

import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.fastjson.JSON
import com.code.demo.R.id
import com.code.demo.R.layout
import com.code.demo.viewmodels.RetrofitUtilsViewModel
import com.code.fastframe.baseactivity.BaseTitleActivity
import com.code.fastframe.eventbus.GlobalEvent
import com.code.fastframe.utils.L
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_retrofitutils.request

@AndroidEntryPoint
class RetrofitUtilsActivity : BaseTitleActivity() {
  var mKey: EditText? = null
  var mList: TextView? = null
  private val viewModel by viewModels<RetrofitUtilsViewModel>()
  override fun setTitleText(): String {
    return this.javaClass.simpleName
  }

  override fun setContentLayout(): Int {
    return layout.activity_retrofitutils
  }

  override fun initView() {
    mKey = findViewById(id.key)
    mList = findViewById(id.list)
    request.setOnClickListener {
      viewModel.requestData()
    }
    viewModel.liveData.observe(this, Observer {
      L.e("ui get")
      mList!!.text = JSON.toJSONString(it)
    })
  }

  override fun initData() {
    super.initData()
  }

  override fun eventComming(globalMsg: GlobalEvent) {}
}