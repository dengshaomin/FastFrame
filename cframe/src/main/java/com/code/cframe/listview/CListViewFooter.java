package com.code.cframe.listview;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.code.cframe.R;
import com.code.cframe.baseview.BaseLayout;
import com.code.cframe.GlobalEvent;

/**
 * Created by dengshaomin on 2017/7/25.
 */

public class CListViewFooter extends BaseLayout {
    private View progress, no_more_tip;

    public CListViewFooter(Context context) {
        super(context);
    }

    public CListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CListViewFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int setContentLayout() {
        return R.layout.clist_view_footer;
    }

    @Override
    public void initView() {
        progress = findViewById(R.id.progress);
        no_more_tip = findViewById(R.id.no_more_tip);
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
    public void eventComming(GlobalEvent globalMsg) {

    }


    @Override
    public void setViewData(Object data) {
        if (data instanceof Boolean && (Boolean) data) {
            no_more_tip.setVisibility(GONE);
            progress.setVisibility(VISIBLE);
        } else {
            no_more_tip.setVisibility(VISIBLE);
            progress.setVisibility(GONE);
        }
    }
}
