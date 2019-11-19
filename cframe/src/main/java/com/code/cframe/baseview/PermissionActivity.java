package com.code.cframe.baseview;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.code.cframe.ciface.IPermissionActivity;
import com.code.cframe.utils.ToastUtils;


public abstract class PermissionActivity extends AppCompatActivity implements IPermissionActivity {

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
            ActivityCompat.requestPermissions(this, applyPermisson.toArray(arrays), MY_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    ToastUtils.showToast(PermissionActivity.this, "部分权限被拒绝~");
                    finish();
                    return;
                }
            }
             permissonSuccess();
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void permissonSuccess() {
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
