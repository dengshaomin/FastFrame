package com.code.codeframlibrary.commons.activity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.code.codeframlibrary.R;
import com.code.codeframlibrary.commons.baseview.BaseItemLayout;
import com.code.codeframlibrary.commons.baseview.BaseTitleActivity;
import com.code.codeframlibrary.commons.ciface.IListActivityCallBack;
import com.code.codeframlibrary.commons.ciface.IListCallBack;
import com.code.codeframlibrary.commons.listview.CCommonViewHolder;
import com.code.codeframlibrary.commons.listview.CListView;
import com.code.codeframlibrary.commons.widgets.EmptyItemTipView;

public abstract class CListActivity<T> extends BaseTitleActivity implements IListCallBack, IListActivityCallBack {

    private List<T> datas;

    private CListView mCListView;


    @Override
    public int setContentLayout() {
        return R.layout.activity_clist;
    }

    @Override
    public void initView() {
        datas = new ArrayList<>();
        mCListView = findViewById(R.id.recycler_view);
        mCListView.setRefreshMode(setRefreshMode());
        mCListView.setSpringBackMode(setSpringBackMode());
        mCListView.setLayoutManager(setLayoutManager() == null ? new LinearLayoutManager(this) : setLayoutManager());
        mCListView.setNeedStateView(needStateView());
        mCListView.setCListAction(datas, this);
        onRefresh(1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CCommonViewHolder(getListItemView() == null ? new EmptyItemTipView(this) : getListItemView());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (datas != null && 0 <= position && position < datas.size()) {
            ((BaseItemLayout) (holder.itemView)).setViewData(datas.get(position));
        }
    }

    public void updateData(List<T> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        mCListView.updateData(datas);
    }
}
