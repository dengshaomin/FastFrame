package com.code.fastframe.baseactivity;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.code.fastframe.ciface.IPermissionActivity;
import com.code.fastframe.utils.ToastUtils;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePermissionActivity extends BaseActivity
    implements IPermissionActivity {

  private static final int MY_PERMISSIONS_REQUEST_CODE = 1;

  private ProgressDialog progressDialog;

  private List<String> applyPermisson;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    List<String> permissonItems = needPermissions();
    if (permissonItems != null && permissonItems.size() > 0) {
      for (String ps : permissonItems) {
        if (ContextCompat.checkSelfPermission(this, ps) != PackageManager.PERMISSION_GRANTED) {
          if (applyPermisson == null) {
            applyPermisson = new ArrayList<>();
          }
          applyPermisson.add(ps);
        }
      }
    }
    if (applyPermisson != null && applyPermisson.size() > 0) {
      String[] arrays = new String[applyPermisson.size()];
      ActivityCompat.requestPermissions(this, applyPermisson.toArray(arrays),
          MY_PERMISSIONS_REQUEST_CODE);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == MY_PERMISSIONS_REQUEST_CODE) {
      List<String> failP = new ArrayList<>();
      for (int i = 0; i < grantResults.length; i++) {
        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
          if (permissions != null && permissions.length >= i) {
            failP.add(permissions[i]);
          }
        }
      }
      if (failP.size() > 0) {
        ToastUtils.showToast("部分权限被拒绝~");
        permissionsFail(failP);
        return;
      }
      permissionsSuccess();
      return;
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override
  public void permissionsSuccess() {

  }

  @Override
  public void permissionsFail(List<String> permissions) {

  }

  @Override
  public List<String> needPermissions() {
    return null;
  }

  public void showProgressDialog() {
    if (progressDialog == null) {
      progressDialog = new ProgressDialog(this);
    }
    if (progressDialog != null && !progressDialog.isShowing()) {
      progressDialog.show();
    }
  }

  public void closeProgressDialog() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }
}
