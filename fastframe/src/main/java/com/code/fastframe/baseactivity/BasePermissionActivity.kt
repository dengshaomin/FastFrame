package com.code.fastframe.baseactivity

import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.code.fastframe.ciface.IPermissionActivity
import com.code.fastframe.utils.ToastUtils
import java.util.ArrayList

abstract class BasePermissionActivity : BaseActivity(), IPermissionActivity {
  private var applyPermisson = mutableListOf<String>()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val permissonItems = needPermissions()
    if (permissonItems != null && permissonItems.size > 0) {
      for (ps in permissonItems) {
        if (ContextCompat.checkSelfPermission(this, ps) != PackageManager.PERMISSION_GRANTED) {
          if (applyPermisson == null) {
            applyPermisson = ArrayList()
          }
          applyPermisson!!.add(ps)
        }
      }
    }
    if (applyPermisson != null && applyPermisson!!.size > 0) {
      val arrays = arrayOfNulls<String>(
          applyPermisson!!.size
      )
      ActivityCompat.requestPermissions(
          this, arrays,
          MY_PERMISSIONS_REQUEST_CODE
      )
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    if (requestCode == MY_PERMISSIONS_REQUEST_CODE) {
      val failP: MutableList<String> = ArrayList()
      for (i in grantResults.indices) {
        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
          if (permissions != null && permissions.size >= i) {
            failP.add(permissions[i])
          }
        }
      }
      if (failP.size > 0) {
        ToastUtils.showToast("部分权限被拒绝~")
        permissionsFail(failP)
        return
      }
      permissionsSuccess()
      return
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  override fun permissionsSuccess() {}
  override fun permissionsFail(permissions: List<String>) {}
  override fun needPermissions(): MutableList<String> {
    return mutableListOf()
  }


  companion object {
    private const val MY_PERMISSIONS_REQUEST_CODE = 1
  }
}