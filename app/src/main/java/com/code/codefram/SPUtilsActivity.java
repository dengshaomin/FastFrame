package com.code.codefram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.code.codeframlibrary.commons.GlobalMsg;
import com.code.codeframlibrary.commons.baseview.BaseTitleActivity;
import com.code.codeframlibrary.commons.ciface.IBasePresent;
import com.code.codeframlibrary.commons.utils.CSPUtils;
import com.code.codeframlibrary.commons.widgets.SuperButton;
import com.github.lazylibrary.util.AssetDatabaseOpenHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SPUtilsActivity extends BaseTitleActivity {


    @BindView(R.id.key)
    EditText mKey;

    @BindView(R.id.value)
    EditText mValue;

    @BindView(R.id.save)
    SuperButton mSave;

    @BindView(R.id.get)
    SuperButton mGet;

    @BindView(R.id.list)
    ListView mList;

    private List<String> str_name = new ArrayList<String>();

    private ArrayAdapter<String> adapter;

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
        return "SPUtils";
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
        return R.layout.activity_sputils;
    }

    @Override
    public void initView() {
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, str_name);
        mList.setAdapter(adapter);
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
                CSPUtils.getInstance(this).put(mKey.getText().toString(), mValue.getText().toString());
                break;
            case R.id.get:
                str_name.clear();
                Map<String, String> values = (Map<String, String>) CSPUtils.getInstance(this).getAll();
                for (String key : values.keySet()) {
                    str_name.add(key + " " + values.get(key));
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public IBasePresent getPresents() {
        return null;
    }
}
