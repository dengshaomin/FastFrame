package com.code.codefram.demo;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.code.cframe.GlobalEvent;
import com.code.cframe.baseview.BaseItemLayout;
import com.code.codefram.R;

/**
 * Created by dengshaomin on 2017/12/4.
 */

public class TestItem extends BaseItemLayout {

    TextView mText;

    public TestItem(Context context) {
        super(context);
    }

    public TestItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public int setContentLayout() {
        return R.layout.item_test;
    }

    @Override
    public void initItemView() {
        mText = getRootView().findViewById(R.id.text);
    }
    @Override
    public void setViewData(Object data) {
        mText.setText(data + "");
    }

}
