package com.code.codefram;

import java.util.List;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.code.codeframlibrary.commons.GlobalMsg;
import com.code.codeframlibrary.commons.baseview.BaseTitleActivity;
import com.code.codeframlibrary.commons.retrofit.GCNetCallBack;
import com.code.codeframlibrary.commons.retrofit.NetInterface;
import com.code.codeframlibrary.commons.retrofit.RetrofitHttpUtil;
import com.code.codeframlibrary.commons.widgets.SuperButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RetrofitUtilsActivity extends BaseTitleActivity {


    @BindView(R.id.key)
    EditText mKey;

    @BindView(R.id.request)
    SuperButton mRequest;

    @BindView(R.id.list)
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
        return "FileUtils";
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


    @OnClick(R.id.request)
    public void onViewClicked() {
        RetrofitHttpUtil.getInstance().getOutUrl(mKey.getText().toString(), null, new GCNetCallBack<String>("0", new NetInterface() {
            @Override
            public void onSuccess(String indentify, String code, String response) {
                mList.setText(response);
            }

            @Override
            public void onError(String indentify, String code, String msg) {
                mList.setText(msg);
            }
        }));
    }
}
