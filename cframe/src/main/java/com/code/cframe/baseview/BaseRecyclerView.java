package com.code.cframe.baseview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshView.SimpleXRefreshListener;
import com.code.cframe.R;
import com.code.cframe.ciface.IBaseRecyclerViewCb;
import com.code.cframe.ciface.IHeadClick;
import com.code.cframe.recyclerview.CHeaderFooterAdapter;

/**
 * Created by dengshaomin on 2017/12/4.
 */

public class BaseRecyclerView extends BaseViewLayout implements IHeadClick, OnClickListener {

    //刷新模式
    public static class Mode {

        public static final int START = 0;

        public static final int END = 1;

        public static final int BOTH = 2;

        public static final int NONE = 3;
    }

    protected RecyclerView mRecyclerview;

    protected XRefreshView mXrefreshview;


    private RelativeLayout clist_root;

    protected int refreMode;

    private Adapter mAdapter;

    private CHeaderFooterAdapter mCHeaderFooterAdapter;

    protected IBaseRecyclerViewCb mIBaseRecyclerViewCb;


    protected BaseRcvFooterView mCListViewFooter;

    protected static final int DEFAULT = -1;

    private static final int WhitchPositionAutoShowLoadMoreFootView = 2;    //2代表滑动倒数第几个触发自动加载

    protected int refreshState = DEFAULT;

    private boolean hasMoreData = true;

    public BaseRecyclerView(Context context, Adapter adapter, IBaseRecyclerViewCb iBaseRecyclerViewCb) {
        super(context);
        setIBaseRecyclerViewCb(iBaseRecyclerViewCb);
        setAdapter(adapter);
    }

    public void setIBaseRecyclerViewCb(IBaseRecyclerViewCb IBaseRecyclerViewCb) {
        mIBaseRecyclerViewCb = IBaseRecyclerViewCb;
    }

    //    public void setCListCallBackLister(IListCallBack CListCallBackLister) {
//        mIBaseRecyclerViewCb = CListCallBackLister;
//    }

    @Override
    public int setContentLayout() {
        return R.layout.clist_view;
    }

    //类似ios的回弹效果
    public void setSpringBackMode(int mode) {
        switch (mode) {
            case Mode.START:
                mXrefreshview.enableRecyclerViewPullDown(true);
                mXrefreshview.enableRecyclerViewPullUp(false);
//                mXrefreshview.setPullLoadEnable(false);
                break;
            case Mode.END:
                mXrefreshview.enableRecyclerViewPullUp(true);
                mXrefreshview.enableRecyclerViewPullDown(false);
                break;
            case Mode.BOTH:
                mXrefreshview.enableRecyclerViewPullUp(true);
                mXrefreshview.enableRecyclerViewPullDown(true);
                break;
            case Mode.NONE:
                mXrefreshview.enableRecyclerViewPullUp(false);
                mXrefreshview.enableRecyclerViewPullDown(false);
                break;
            default:
                setSpringBackMode(Mode.NONE);
                break;
        }
    }

    @Override
    public void initView() {
        mRecyclerview = getRootView().findViewById(R.id.recyclerview);
        mXrefreshview = getRootView().findViewById(R.id.xrefreshview);
        clist_root = getRootView().findViewById(R.id.clist_root);
        initXrefreshView();

    }

    private void initXrefreshView() {
        //设置刷新完成以后，headerview固定的时间
        mXrefreshview.setPinnedTime(300);
        mXrefreshview.setMoveForHorizontal(true);
        mXrefreshview.setPullLoadEnable(true);
        mXrefreshview.setAutoLoadMore(false);
        setSpringBackMode(Mode.BOTH);
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
                refreshState = Mode.START;
                if (mIBaseRecyclerViewCb != null) {
                    mIBaseRecyclerViewCb.onRefresh(refreshState);
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

    protected void needLoadMore() {
        if (refreMode == Mode.START || refreMode == Mode.NONE || refreshState == Mode.END) {
            return;
        }
        refreshState = Mode.END;
        if (mIBaseRecyclerViewCb != null) {
            mIBaseRecyclerViewCb.onRefresh(refreshState);
        }
    }

    public void addHeadView(View view) {
        if (mCHeaderFooterAdapter != null) {
            mCHeaderFooterAdapter.addHeaderItem(view);
        }
    }

    public void removeHeadView(View view) {
        if (mCHeaderFooterAdapter != null) {
            mCHeaderFooterAdapter.removeHeaderView(view);
        }
    }

    public void removeHeadView() {
        if (mCHeaderFooterAdapter != null) {
            mCHeaderFooterAdapter.removeHeaderItem();
        }
    }

    public void addFooterView(View view) {
        if (mCHeaderFooterAdapter != null) {
            mCHeaderFooterAdapter.addFooterItem(view);
        }
    }

    public void removeFooterView(View view) {
        if (mCHeaderFooterAdapter != null) {
            mCHeaderFooterAdapter.removeFootView(view);
        }
    }

    public void removeFooterView() {
        if (mCHeaderFooterAdapter != null) {
            mCHeaderFooterAdapter.removeFooterItem();
        }
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

    protected void initFootAndAdapter() {
        if (mCHeaderFooterAdapter == null) {
            mCHeaderFooterAdapter = new CHeaderFooterAdapter(mAdapter, this);
        }
        if (mCListViewFooter == null) {
            mCListViewFooter = new BaseRcvFooterView(getContext());
            mCHeaderFooterAdapter.addFooterItem(mCListViewFooter);
        }
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

    public void setAdapter(Adapter adapter) {
        this.mAdapter = adapter;
        initFootAndAdapter();
    }

    public void setRefreshMode(int mode) {
        refreMode = mode;
        if (mCListViewFooter != null) {
            mCListViewFooter.setVisibility(refreMode == Mode.END || refreMode == Mode.BOTH ? VISIBLE : GONE);
        }
        switch (mode) {
            case Mode.START:
                mXrefreshview.setPullRefreshEnable(true);
                mXrefreshview.setPullLoadEnable(false);
                break;
            case Mode.END:
                mXrefreshview.setPullRefreshEnable(false);
                mXrefreshview.setPullLoadEnable(true);
                break;
            case Mode.BOTH:
                mXrefreshview.setPullRefreshEnable(true);
                mXrefreshview.setPullLoadEnable(false);
                break;
            case Mode.NONE:
                mXrefreshview.setPullRefreshEnable(false);
                mXrefreshview.setPullLoadEnable(false);
                break;
            default:
                setRefreshMode(Mode.BOTH);
                break;
        }
    }

    public void refreshComplete() {
        this.hasMoreData = hasMoreData;
        if (refreshState == Mode.START) {
            mXrefreshview.stopRefresh();
        } else if (refreshState == Mode.END) {
            mXrefreshview.stopLoadMore();
        }
        if (mCListViewFooter != null) {
            mCListViewFooter.setViewData(hasMoreData);
        }
        refreshState = DEFAULT;
    }

    @Override
    public void setViewData(Object data) {

    }

    @Override
    public void onHeadFootClickLister(View view, Object data, int position) {
        if (mIBaseRecyclerViewCb != null) {
            mIBaseRecyclerViewCb.onHeadFootClickLister(view, data, position);
        }
    }

    @Override
    public void onClick(View v) {
    }
}
