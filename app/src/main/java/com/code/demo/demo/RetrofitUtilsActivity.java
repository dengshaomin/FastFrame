package com.code.demo.demo;

import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.code.demo.demo.model.LogisticsModel;
import com.code.demo.demo.presents.TestPresent;
import com.code.demo.demo.presents.TestPresent.ITest;
import com.code.fastframe.utils.ToastUtils;
import com.code.demo.R;
import com.code.fastframe.eventbus.GlobalEvent;
import com.code.fastframe.baseactivity.BaseTitleActivity;
import com.code.fastframe.widgets.SuperButton;


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
    public void eventComming(GlobalEvent globalMsg) {

    }


    @Override
    public void showData(List<LogisticsModel> data) {
        mList.setText(JSON.toJSONString(data) + "");
    }

    @Override
    public void showError(String s) {
        ToastUtils.showToast(s);
    }

    @Override
    public void onClick(View v) {
        mTestPresent.getLogistics();
    }
}
