package com.code.codefram;

import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.code.codefram.model.LogisticsModel;
import com.code.codefram.presents.TestPresent;
import com.code.codefram.presents.TestPresent.ITest;
import com.code.codeframlibrary.commons.GlobalMsg;
import com.code.codeframlibrary.commons.baseview.BaseTitleActivity;
import com.code.codeframlibrary.commons.ciface.IBasePresent;
import com.code.codeframlibrary.commons.widgets.SuperButton;
import com.github.lazylibrary.util.ToastUtils;


public class RetrofitUtilsActivity extends BaseTitleActivity implements ITest, OnClickListener {


    EditText mKey;

    SuperButton mRequest;

    TextView mList;

    @Override
    public boolean needTitle() {
        return true;
    }

    @Override
    public int setTitleLeftImage() {
        return 0;
    }

    @Override
    public int setTitleRightImage() {
        return 0;
    }

    @Override
    public String setTitleText() {
        return "RetrofitUtils";
    }


    @Override
    public void titleRightClick() {

    }

    @Override
    public List<String> needPermissions() {
        return null;
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_retrofitutils;
    }

    @Override
    public void initView() {
        mKey = findViewById(R.id.key);
        mRequest = findViewById(R.id.request);
        mList = findViewById(R.id.list);
        mRequest.setOnClickListener(this);
    }

    @Override
    public void initBundleData() {

    }

    @Override
    public void getNetData() {

    }

    @Override
    public List<String> regeistEvent() {
        return null;
    }

    @Override
    public void eventComming(GlobalMsg globalMsg) {

    }

    @Override
    public void setViewData(Object data) {
    }


    @Override
    public IBasePresent getPresents() {
        return new TestPresent(this);
    }

    @Override
    public void showData(List<LogisticsModel> data) {
        mList.setText(JSON.toJSONString(data) + "");
    }

    @Override
    public void showError(String s) {
        ToastUtils.showToast(this, s);
    }

    @Override
    public void onClick(View v) {
        ((TestPresent) mIBasePresents).getLogistics();
    }
}
