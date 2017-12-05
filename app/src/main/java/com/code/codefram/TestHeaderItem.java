package com.code.codefram;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.code.codeframlibrary.commons.BaseLayout;
import com.code.codeframlibrary.commons.GlobalMsg;

import butterknife.BindView;

/**
 * Created by dengshaomin on 2017/12/4.
 */

public class TestHeaderItem extends BaseLayout {

    @BindView(R.id.text)
    TextView mText;

    public TestHeaderItem(Context context) {
        super(context);
    }

    public TestHeaderItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestHeaderItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int setContentLayout() {
        return R.layout.item_headtest;
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
