package com.code.codefram;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.code.codeframlibrary.commons.GlobalMsg;
import com.code.codeframlibrary.commons.baseview.BaseTitleActivity;
import com.code.codeframlibrary.commons.ciface.IBasePresent;
import com.code.codeframlibrary.commons.utils.CFlieUtils;
import com.code.codeframlibrary.commons.utils.CSPUtils;
import com.code.codeframlibrary.commons.widgets.SuperButton;
import com.github.lazylibrary.util.FileUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FileUtilsActivity extends BaseTitleActivity {


    @BindView(R.id.key)
    EditText mKey;

    @BindView(R.id.value)
    EditText mValue;

    @BindView(R.id.save)
    SuperButton mSave;

    @BindView(R.id.get)
    SuperButton mGet;

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
        return R.layout.activity_fileutils;
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


    @OnClick({R.id.save, R.id.get})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.save:
                if (TextUtils.isEmpty(mKey.getText().toString())) {
                    return;
                }
                CFlieUtils.saveStrToFile(mKey.getText().toString(), mValue.getText().toString());
                break;
            case R.id.get:
                mList.setText(CFlieUtils.readFile(mKey.getText().toString()));
                break;
        }
    }

    @Override
    public IBasePresent getPresents() {
        return null;
    }
}
