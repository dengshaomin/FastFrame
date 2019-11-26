package com.code.cframe.recyclerview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.code.cframe.R;
import com.code.cframe.baseview.BaseRecyclerView;
import com.code.cframe.ciface.IBaseRecyclerViewCb;
import com.code.cframe.ciface.IFastRecyclerViewCb;
import com.code.cframe.utils.CollectionUtils;

/**
 * Created by dengshaomin on 2017/12/4.
 */

public class FastRecyclerView<T> extends BaseRecyclerView {

    private int pageSize = 10;

    private int pageIndex = 1;


    private List<T> datas;

    public FastRecyclerView(Context context, Adapter adapter, IBaseRecyclerViewCb iBaseRecyclerViewCb) {
        super(context, adapter, iBaseRecyclerViewCb);
    }

    public FastRecyclerView(Context context) {
        super(context);
    }

    public FastRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FastRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public List<T> getDatas() {
        return datas;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    @Override
    public void initView() {
        super.initView();
        mRecyclerview = getRootView().findViewById(R.id.recyclerview);
        mXrefreshview = getRootView().findViewById(R.id.xrefreshview);
        initFootAndAdapter();
    }

    public void updateData(List<T> datas) {
        if (CollectionUtils.isEmpty(datas)) {
            refreshComplete(false);
            return;
        }
        if (this.datas == null) {
            this.datas = new ArrayList<>();
        }
        if (refreshState == Mode.START) {
            this.datas.clear();
        }
        this.datas.addAll(datas);
        refreshComplete(mAdapter.getItemCount() % pageSize == 0);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initFootAndAdapter() {
        if (mAdapter == null) {
            mAdapter = new Adapter() {
                @Override
                public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    return mIBaseRecyclerViewCb == null ? null : ((IFastRecyclerViewCb) mIBaseRecyclerViewCb).onCreateViewHolder(parent, viewType);
                }

                @Override
                public void onBindViewHolder(final ViewHolder holder, final int position) {
                    if (mIBaseRecyclerViewCb != null) {
                        ((IFastRecyclerViewCb) mIBaseRecyclerViewCb).onBindViewHolder(holder, position);
                        if (holder != null && holder.itemView != null && datas != null && 0 <= position && position < datas.size()) {
                            holder.itemView.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ((IFastRecyclerViewCb) mIBaseRecyclerViewCb).onItemClickLister(holder.itemView, datas.get(position), position);
                                }
                            });
                        }
                    }
                }

                @Override
                public int getItemCount() {
                    return datas == null ? 0 : datas.size();
                }
            };
        }
        super.initFootAndAdapter();

    }

    public void setLayoutManager(LayoutManager layout) {
        if (mRecyclerview != null) {
            mRecyclerview.setLayoutManager(layout);
        }
    }

    public void addItemDecoration(ItemDecoration decor) {
        if (mRecyclerview != null) {
            mRecyclerview.addItemDecoration(decor);
        }
    }

//    public void setCListActionInterface(CListRefreshInterface CListRefreshInterface) {
//        mCListActionInterface = CListRefreshInterface;
//    }

    //    public void setAdapter(Adapter adapter) {
//        this.mAdapter = adapter;
//        if (mCHeaderFooterAdapter == null) {
//            mCHeaderFooterAdapter = new CHeaderFooterAdapter(adapter, this);
//        } else {
//            mCHeaderFooterAdapter.setAdapter(adapter);
//        }
//        mRecyclerview.setAdapter(mCHeaderFooterAdapter);
//    }
    @Override
    protected void needLoadMore() {
        if (refreMode == Mode.START || refreMode == Mode.NONE || refreshState != DEFAULT) {
            return;
        }
        if (mAdapter.getItemCount() == 0 || mAdapter.getItemCount() % pageSize != 0) {
            return;
        }
        if (mCListViewFooter != null) {
            mCListViewFooter.setViewData(hasMoreData());
        }
        this.pageIndex = mAdapter.getItemCount() / pageSize + 1;
        refreshState = Mode.END;
        if (mIBaseRecyclerViewCb != null) {
            mIBaseRecyclerViewCb.onRefresh(refreshState);
        }
    }

    private boolean hasMoreData() {
        if (mAdapter == null) {
            return false;
        }
        return mAdapter.getItemCount() == 0 || mAdapter.getItemCount() % pageSize != 0 ? false : true;
    }

//    public void refreshComplete() {
//        if (refreshState == Mode.START) {
//            mXrefreshview.stopRefresh();
//        } else if (refreshState == Mode.END) {
//            mXrefreshview.stopLoadMore();
//        }
//        if (mCListViewFooter != null) {
//            mCListViewFooter.setViewData(hasMoreData());
//        }
//        refreshState = DEFAULT;
//    }
}
