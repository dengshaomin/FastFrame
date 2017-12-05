package com.code.codeframlibrary.commons.listview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshView.SimpleXRefreshListener;
import com.code.codeframlibrary.R;
import com.code.codeframlibrary.commons.BaseLayout;
import com.code.codeframlibrary.commons.GlobalMsg;

import butterknife.BindView;

/**
 * Created by dengshaomin on 2017/12/4.
 */

public class CListView<T> extends BaseLayout implements CHeadClickInterface {

    public static final int START = 0;

    public static final int END = 1;

    public static final int BOTH = 2;

    public static final int NONE = 3;

    public static final int SUCCESS = 0;

    public static final int ERROR = 1;

    public static final int EMPTY = 2;

    RecyclerView mRecyclerview;

    XRefreshView mXrefreshview;

    private int pageSize = 10;

    private int pageIndex = 1;

    private int refreMode;

    private Adapter mAdapter;

    private CHeaderFooterAdapter mCHeaderFooterAdapter;

    private CListCallBackInterface mCListCallBackLister;


    private CListViewFooter mCListViewFooter;

    private static final int DEFAULT = -1;

    private static final int WhitchPositionAutoShowLoadMoreFootView = 2;    //2代表滑动倒数第几个触发自动加载

    private int refreshState = DEFAULT;

    private List<T> datas;

    public CListView(Context context) {
        super(context);
    }

    public CListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public void setCListCallBackLister(CListCallBackInterface CListCallBackLister) {
//        mCListCallBackLister = CListCallBackLister;
//    }

    @Override
    public int setContentLayout() {
        return R.layout.clist_view;
    }

    public void setSpringBackMode(int mode) {
        switch (mode) {
            case START:
                mXrefreshview.enableRecyclerViewPullDown(true);
                mXrefreshview.enableRecyclerViewPullUp(false);
                mXrefreshview.setPullLoadEnable(false);
                break;
            case END:
                mXrefreshview.enableRecyclerViewPullUp(true);
                mXrefreshview.enableRecyclerViewPullDown(false);
                break;
            case BOTH:
                mXrefreshview.enableRecyclerViewPullUp(true);
                mXrefreshview.enableRecyclerViewPullDown(true);
                break;
            case NONE:
                mXrefreshview.enableRecyclerViewPullUp(false);
                mXrefreshview.enableRecyclerViewPullDown(false);
                break;
        }
    }

    @Override
    public void initView() {
        mRecyclerview = getRootView().findViewById(R.id.recyclerview);
        mXrefreshview = getRootView().findViewById(R.id.xrefreshview);
        initFootAndAdapter();
        initXrefreshView();

    }

    private void initXrefreshView() {
        //设置刷新完成以后，headerview固定的时间
        mXrefreshview.setPinnedTime(300);
        mXrefreshview.setMoveForHorizontal(true);
        mXrefreshview.setPullLoadEnable(true);
        mXrefreshview.setAutoLoadMore(false);
        setSpringBackMode(BOTH);
        //禁止手动上拉加载更过
        mXrefreshview.enableReleaseToLoadMore(false);

        mXrefreshview.enablePullUpWhenLoadCompleted(true);
        //设置静默加载时提前加载的item个数
//        xefreshView1.setPreLoadCount(4);
        //设置Recyclerview的滑动监听
        mXrefreshview.setOnRecyclerViewScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (isScrollBottom()) {
                        needLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
                if (dx > 0 && isScrollBottom()) {
                    needLoadMore();
                }
            }
        });
        mXrefreshview.setXRefreshViewListener(new SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {
                pageIndex = 1;
                refreshState = START;
                if (mCListCallBackLister != null) {
                    mCListCallBackLister.onRefresh(pageIndex);
                } else {
                    mXrefreshview.stopRefresh();
                }
            }

            @Override
            public void onLoadMore(boolean isSilence, int index) {
                needLoadMore();
            }
        });

    }

    private boolean isScrollBottom() {
        //recyclerView.canScrollVertically(1) //是否滑动到最底部
        //recyclerView.canScrollVertically(-1)  //是否滑动到最顶部
        int realyCount = mAdapter.getItemCount();
        if (mRecyclerview.getLayoutManager() == null) {
            return true;
        }
        if (mRecyclerview.getLayoutManager() instanceof LinearLayoutManager) {
            return realyCount - WhitchPositionAutoShowLoadMoreFootView <= ((LinearLayoutManager) mRecyclerview.getLayoutManager())
                    .findLastVisibleItemPosition();
        } else if (mRecyclerview.getLayoutManager() instanceof GridLayoutManager) {
            return realyCount - WhitchPositionAutoShowLoadMoreFootView <= ((GridLayoutManager) mRecyclerview.getLayoutManager())
                    .findLastVisibleItemPosition();
        } else if (mRecyclerview.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            int[] into = new int[0];
            ((StaggeredGridLayoutManager)
                    mRecyclerview.getLayoutManager()).findLastVisibleItemPositions(into);
            if (into != null) {
                return realyCount - WhitchPositionAutoShowLoadMoreFootView <= into[into.length - 1];
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void setCListAction(List<T> datas, CListCallBackInterface cListCallBackInterface) {
        if (datas != null) {
            if (this.datas == null) {
                this.datas = new ArrayList<>();
            } else {
                this.datas.clear();
            }
            this.datas.addAll(datas);
        }
        mCListCallBackLister = cListCallBackInterface;
        if (datas != null && datas.size() > 0) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void updateData(List<T> datas) {
        if (datas == null) {
            return;
        }
        if (this.datas == null) {
            this.datas = new ArrayList<>();
        }
        if (pageIndex == 1) {
            this.datas.clear();
        }
        this.datas.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    private void initFootAndAdapter() {
        if (mAdapter == null) {
            mAdapter = new Adapter() {
                @Override
                public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    return mCListCallBackLister == null ? null : mCListCallBackLister.onCreateViewHolder(parent, viewType);
                }

                @Override
                public void onBindViewHolder(final ViewHolder holder, final int position) {
                    if (mCListCallBackLister != null) {
                        mCListCallBackLister.onBindViewHolder(holder, position);
                        if (holder != null && holder.itemView != null && datas != null && 0 <= position && position < datas.size()) {
                            holder.itemView.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mCListCallBackLister.onItemClickLister(holder.itemView, datas.get(position), position);
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
        if (mCHeaderFooterAdapter == null) {
            mCHeaderFooterAdapter = new CHeaderFooterAdapter(mAdapter, this);
        }
        if (mCListViewFooter == null) {
            mCListViewFooter = new CListViewFooter(getmContext());
        }
        mCHeaderFooterAdapter.addFooterItem(mCListViewFooter);
        mRecyclerview.setAdapter(mCHeaderFooterAdapter);

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

    public void setRefreshMode(int mode) {
        refreMode = mode;
        switch (mode) {
            case START:
                mXrefreshview.setPullRefreshEnable(true);
                mXrefreshview.setPullLoadEnable(false);
                break;
            case END:
                mXrefreshview.setPullRefreshEnable(false);
                mXrefreshview.setPullLoadEnable(true);
                break;
            case BOTH:
                mXrefreshview.setPullRefreshEnable(true);
                mXrefreshview.setPullLoadEnable(false);
                break;
            case NONE:
                mXrefreshview.setPullRefreshEnable(false);
                mXrefreshview.setPullLoadEnable(false);
                break;
        }
    }

    private void needLoadMore() {
        if (refreMode == START || refreMode == NONE || refreshState == END) {
            return;
        }
//        if (footView == null) {
//            footView = new CodeRecyclerViewFooter(getmContext());
//        }
//        if (headerAndFooterWrapper == null) {
//            headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
//        }
//        if (!headerAndFooterWrapper.hasFootView(footView)) {
//            headerAndFooterWrapper.addFootView(footView);
//            headerAndFooterWrapper.notifyDataSetChanged();
//        }
        if (mAdapter.getItemCount() == 0 || mAdapter.getItemCount() % pageSize != 0) {
            return;
        }
        if (mCListViewFooter != null) {
//            footView.setVisibility(adapter != null && adapter.getItemCount() != 0 ? VISIBLE : GONE);
            mCListViewFooter.setViewData(hasMoreData());
        }
        this.pageIndex = mAdapter.getItemCount() / pageSize + 1;
        refreshState = END;
        if (mCListCallBackLister != null) {
            mCListCallBackLister.onRefresh(this.pageIndex);
        }
    }

    private boolean hasMoreData() {
        if (mAdapter == null) {
            return false;
        }
        return mAdapter.getItemCount() == 0 || mAdapter.getItemCount() % pageSize != 0 ? false : true;
    }

    public void refreshComplete(int state) {
        if (refreshState == START) {
            mXrefreshview.stopRefresh();
        } else if (refreshState == END) {
            mXrefreshview.stopLoadMore();
        }
        refreshState = DEFAULT;
        if (mCListViewFooter != null) {
            mCListViewFooter.setViewData(hasMoreData());
        }
        switch (state) {
            case SUCCESS:
                break;
            case ERROR:
                break;
            case EMPTY:
                break;
        }
    }

    @Override
    public void initBundleData() {
        datas = new ArrayList<>();
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

    }

    @Override
    public void onHeadFootClickLister(View view, Object data, int position) {
        if (mCListCallBackLister != null) {
            mCListCallBackLister.onHeadFootClickLister(view, data, position);
        }
    }
}
