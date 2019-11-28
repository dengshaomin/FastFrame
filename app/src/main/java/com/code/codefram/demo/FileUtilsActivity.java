package com.code.codefram.demo;

import java.util.List;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.code.fastframe.eventbus.GlobalEvent;
import com.code.fastframe.baseactivity.BaseTitleActivity;
import com.code.fastframe.utils.FileUtils;
import com.code.fastframe.widgets.SuperButton;
import com.code.codefram.R;


public class FileUtilsActivity extends BaseTitleActivity implements OnClickListener {


    EditText mKey;

    EditText mValue;

    SuperButton mSave;

    SuperButton mGet;

    TextView mList;

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
        mKey = findViewById(R.id.key);
        mValue = findViewById(R.id.value);
        mSave = findViewById(R.id.save);
        mGet = findViewById(R.id.get);
        mList = findViewById(R.id.list);
        mSave.setOnClickListener(this);
        mGet.setOnClickListener(this);
    }

    @Override
    public void getNetData() {

    }

    @Override
    public List<String> regeistEvent() {
        return null;
    }

    @Override
    public void eventComming(GlobalEvent globalMsg) {

    }


    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.save:
                if (TextUtils.isEmpty(mKey.getText().toString())) {
                    return;
                }
                FileUtils.saveStrToFileInside(mKey.getText().toString(), mValue.getText().toString());
                break;
            case R.id.get:
                mList.setText(FileUtils.readFileInside(mKey.getText().toString()));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                if (TextUtils.isEmpty(mKey.getText().toString())) {
                    return;
                }
                FileUtils.saveStrToFileInside(mKey.getText().toString(), mValue.getText().toString());
                break;
            case R.id.get:
                mList.setText(FileUtils.readFileInside(mKey.getText().toString()));
                break;
        }
    }
}
