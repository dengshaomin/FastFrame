package com.code.demo.demo

import android.Manifest.permission
import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.TextView
import com.code.demo.R.id
import com.code.demo.R.layout
import com.code.fastframe.baseactivity.BasePermissionActivity
import com.code.fastframe.extension.tryOrNull
import com.code.fastframe.utils.AppUtils
import com.iqiyi.extension.navigateActivity
import kotlinx.android.synthetic.main.activity_main.lv

class MainActivity : BasePermissionActivity() {
  private var str_name =
    mutableListOf<Class<*>?>(
        BaseRecyclerSimpleActivity::class.java, FrescoActivity::class.java,
        SPUtilsActivity::class.java, SuperButtonActivity::class.java, FileUtilsActivity::class.java,
        RetrofitUtilsActivity::class.java, BannerActivity::class.java, TabBarActivity::class.java,
        TabLayoutActivity::class.java, FastRecycleSimpleActivity::class.java,
        FastTitleSimpleActivity::class.java,
        LazyViewPagerActivity::class.java, ShareActivity::class.java
    )
  private var adapter: ArrayAdapter<Class<*>?>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
    val version = findViewById<TextView>(id.version)
    version.text = "version:" + AppUtils.getVerName(this)
    adapter = ArrayAdapter<Class<*>?>(this, R.layout.simple_list_item_1, str_name)
    lv!!.adapter = adapter
    lv!!.onItemClickListener = OnItemClickListener { parent, view, position, id ->
      //new Thread(new Runnable() {
      //    @Override
      //    public void run() {
      //        String appId = AGConnectServicesConfig.fromContext(MainActivity.this).getString("client/app_id");
      //        try {
      //            String token = HmsInstanceId.getInstance(MainActivity.this).getToken(appId, "HCM");
      //            int a = 1;
      //        } catch (ApiException e) {
      //            e.printStackTrace();
      //        }
      //    }
      //}).start();
      startActivity(Intent(this@MainActivity,str_name.get(position)))
    }
  }

  override fun onResume() {
    super.onResume()
//    navigateActivity<RetrofitUtilsActivity>()
  }
  override fun needPermissions(): MutableList<String> {
    return mutableListOf(
        permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE, permission.CAMERA
    )
  }
}