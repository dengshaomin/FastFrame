package com.code.cframe.listview;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.code.cframe.R;
import com.code.cframe.GlobalMsg;
import com.code.cframe.baseview.BaseLayout;
import com.code.cframe.ciface.IListReLoad;

/**
 * Created by dengshaomin on 2017/12/8.
 */

public class CListStateView extends BaseLayout implements OnClickListener {

    public static final int EMPTY = 0;

    public static final int ERROR = 1;

    public static final int CONTENT = 2;

    public static final int LOADING = 3;

    private int currentState = 0;

    private int emptyResource = 0;

    private int errorResource = 0;

    private String errorTip = "";

    private String emptyTip = "";

    private String loadingTip = "";

    private ImageView image;

    private TextView tip;

    private ProgressBar progress;

    private IListReLoad mIListReLoad;

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

    public void setIListReLoad(IListReLoad IListReLoad) {
        mIListReLoad = IListReLoad;
    }

    @Override
    public void initView() {
        image = getRootView().findViewById(R.id.image);
        tip = getRootView().findViewById(R.id.tip);
        progress = getRootView().findViewById(R.id.progress);
        setOnClickListener(this);
    }

    @Override
    public void initBundleData() {
        this.errorResource = R.drawable.clist_error;
        this.errorTip = "数据获取异常，点击刷新~";
        this.emptyResource = R.drawable.clist_empty;
        this.emptyTip = "没有数据~";
        this.loadingTip = "正在加载...";
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
        initBundleData();
        if (EMPTY == Integer.parseInt(data + "")) {
            setVisibility(VISIBLE);
            progress.setVisibility(GONE);
            image.setVisibility(VISIBLE);
            image.setBackgroundResource(emptyResource);
            tip.setText(emptyTip);
        } else if (ERROR == Integer.parseInt(data + "")) {
            setVisibility(VISIBLE);
            progress.setVisibility(GONE);
            image.setVisibility(VISIBLE);
            image.setBackgroundResource(errorResource);
            tip.setText(errorTip);
        } else if (CONTENT == Integer.parseInt(data + "")) {
            setVisibility(GONE);
        } else if (LOADING == Integer.parseInt(data + "")) {
            setVisibility(VISIBLE);
            progress.setVisibility(VISIBLE);
            image.setVisibility(GONE);
            tip.setText(loadingTip);
        } else {
            setVisibility(GONE);

        }
    }

    public CListStateView setEmptyImage(int resource) {
        if (resource > 0) {
            this.emptyResource = resource;
        }
        return this;
    }

    public CListStateView setEmptyTip(String emptyTip) {
        if (!TextUtils.isEmpty(emptyTip)) {
            this.emptyTip = emptyTip;
        }
        return this;
    }

    public CListStateView setErrorImage(int resource) {
        if (resource > 0) {
            this.errorResource = resource;
        }
        return this;
    }

    public CListStateView setErrorTip(String errorTip) {
        if (!TextUtils.isEmpty(errorTip)) {
            this.errorTip = errorTip;
        }
        return this;
    }

    public CListStateView setLoadingTip(String loadingTip) {
        if (!TextUtils.isEmpty(loadingTip)) {
            this.loadingTip = loadingTip;
        }
        return this;
    }

    @Override
    public void onClick(View v) {
        if (mIListReLoad != null) {
            setViewData(LOADING);
            mIListReLoad.reLoad();
        }
    }
}
