package com.code.cframe.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


import com.code.cframe.ciface.IBaseViewLayout;


/**
 * Created by dengshaomin on 2016/10/21.
 */
public abstract class BaseViewLayout extends LinearLayout implements IBaseViewLayout {

    private View rootView;


    @Override
    public View getRootView() {
        return rootView;
    }

    private Context mContext;

    public BaseViewLayout(Context context) {
        this(context, null);
    }

    public BaseViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public BaseViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        int layoutId = this.setContentLayout();
        if (layoutId == 0) {
            throw new RuntimeException("setLayout first~~");
        }
        rootView = LayoutInflater.from(mContext).inflate(layoutId, this);
        if (rootView != null) {
            this.initView();
            initData();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public View findView(int id) {
        return rootView == null ? null: rootView.findViewById(id);
    }
}
