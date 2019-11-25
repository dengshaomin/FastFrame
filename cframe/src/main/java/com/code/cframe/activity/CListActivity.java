package com.code.cframe.activity;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import com.code.cframe.R;
import com.code.cframe.baseview.BaseItemLayout;
import com.code.cframe.ciface.IListActivityCallBack;
import com.code.cframe.ciface.IListCallBack;
import com.code.cframe.listview.CCommonViewHolder;
import com.code.cframe.listview.CListView;
import com.code.cframe.widgets.EmptyItemTipView;

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
        if (mCListView.getPageIndex() == 1) {
            this.datas.clear();
        }
        this.datas.addAll(datas);
        mCListView.updateData(datas);
    }
}
