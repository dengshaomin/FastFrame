package com.code.codefram;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.code.codeframlibrary.commons.baseview.BaseLayout;
import com.code.codeframlibrary.commons.GlobalMsg;

import butterknife.BindView;

/**
 * Created by dengshaomin on 2017/12/4.
 */

public class TestItem extends BaseLayout {

    @BindView(R.id.text)
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
    public void initView() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

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
        mText.setText(data + "");
    }
}
