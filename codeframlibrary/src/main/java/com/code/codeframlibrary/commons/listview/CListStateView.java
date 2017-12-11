package com.code.codeframlibrary.commons.listview;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.codeframlibrary.R;
import com.code.codeframlibrary.commons.GlobalMsg;
import com.code.codeframlibrary.commons.baseview.BaseLayout;

/**
 * Created by dengshaomin on 2017/12/8.
 */

public class CListStateView extends BaseLayout {

    public static final int EMPTY = 0;

    public static final int ERROR = 1;

    private int currentState = 0;

    private int emptyResource = 0;

    private int errorResource = 0;

    private String errorTip = "";

    private String emptyTip = "";

    private ImageView image;

    private TextView tip;

    public CListStateView(Context context) {
        super(context);
    }

    public CListStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CListStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int setContentLayout() {
        return R.layout.clist_state_view;
    }

    @Override
    public void initView() {
        image = getRootView().findViewById(R.id.image);
        tip = getRootView().findViewById(R.id.tip);
    }

    @Override
    public void initBundleData() {
        this.errorResource = R.drawable.clist_error;
        this.errorTip = "数据获取异常，点击刷新~";
        this.emptyResource = R.drawable.clist_empty;
        this.emptyTip = "没有数据~";
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
        currentState = (int) data;
        if (EMPTY == Integer.parseInt((String) data)) {
            image.setBackgroundResource(emptyResource);
            tip.setText(emptyTip);
        } else if (ERROR == Integer.parseInt((String) data)) {
            image.setBackgroundResource(errorResource);
            tip.setText(errorTip);
        }
    }

    public void setEmptyImage(int resource, String emptyTip) {
        if (resource > 0) {
            this.emptyResource = resource;
        }
        if (!TextUtils.isEmpty(emptyTip)) {
            this.emptyTip = emptyTip;
        }
    }

    public void setErrorImage(int resource, String errorTip) {
        if (resource > 0) {
            this.errorResource = resource;
        }
        if (!TextUtils.isEmpty(errorTip)) {
            this.errorTip = errorTip;
        }
    }
}
