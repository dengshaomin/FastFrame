package com.code.demo.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.code.fastframe.eventbus.GlobalEvent;
import com.code.fastframe.baseactivity.BaseTitleActivity;
import com.code.fastframe.utils.SharedPreferencesUtils;
import com.code.fastframe.widgets.SuperButton;
import com.code.demo.R;


public class SPUtilsActivity extends BaseTitleActivity implements OnClickListener {


    EditText mKey;

    EditText mValue;

    SuperButton mSave;

    SuperButton mGet;

    ListView mList;

    private List<String> str_name = new ArrayList<String>();

    private ArrayAdapter<String> adapter;


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
        mKey = findViewById(R.id.key);
        mValue = findViewById(R.id.value);
        mSave = findViewById(R.id.save);
        mGet = findViewById(R.id.get);
        mList = findViewById(R.id.list);
        mSave.setOnClickListener(this);
        mGet.setOnClickListener(this);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, str_name);
        mList.setAdapter(adapter);
    }


    @Override
    public void requestNetData() {

    }


    @Override
    public void eventComming(GlobalEvent globalMsg) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                if (TextUtils.isEmpty(mKey.getText().toString())) {
                    return;
                }
                SharedPreferencesUtils.getInstance().put(mKey.getText().toString(), mValue.getText().toString());
                break;
            case R.id.get:
                str_name.clear();
                Map<String, String> values = (Map<String, String>) SharedPreferencesUtils.getInstance().getAll();
                for (String key : values.keySet()) {
                    str_name.add(key + " " + values.get(key));
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
