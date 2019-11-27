package com.code.codefram;

import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.code.cframe.utils.ToastUtils;
import com.code.codefram.model.LogisticsModel;
import com.code.codefram.presents.TestPresent;
import com.code.codefram.presents.TestPresent.ITest;
import com.code.cframe.GlobalEvent;
import com.code.cframe.baseactivity.BaseTitleActivity;
import com.code.cframe.widgets.SuperButton;


public class RetrofitUtilsActivity extends BaseTitleActivity implements ITest, OnClickListener {


    EditText mKey;

    SuperButton mRequest;

    TextView mList;

    TestPresent mTestPresent;

    @Override
    public String setTitleText() {
        return this.getClass().getSimpleName();
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
    public void initData() {
        super.initData();
        mTestPresent = new TestPresent(this, this);
    }

    @Override
    public List<String> regeistEvent() {
        return null;
    }

    @Override
    public void eventComming(GlobalEvent globalMsg) {

    }


    @Override
    public void showData(List<LogisticsModel> data) {
        mList.setText(JSON.toJSONString(data) + "");
    }

    @Override
    public void showError(String s) {
        ToastUtils.showToast( s);
    }

    @Override
    public void onClick(View v) {
        mTestPresent.getLogistics();
    }
}
