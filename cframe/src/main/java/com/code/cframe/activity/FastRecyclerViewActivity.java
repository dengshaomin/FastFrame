package com.code.cframe.activity;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;

import com.code.cframe.R;
import com.code.cframe.baseview.BaseRecyclerView.Mode;
import com.code.cframe.baseview.PageStateView;
import com.code.cframe.baseview.PageStateView.State;
import com.code.cframe.ciface.IFastRecyclerViewCb;
import com.code.cframe.recyclerview.FastRecyclerView;
import com.code.cframe.utils.CollectionUtils;

public abstract class FastRecyclerViewActivity<T> extends BaseTitleActivity {

    private List<T> datas;

    private FastRecyclerView fast_rcv_view;

    private PageStateView page_state_view;

    @Override
    public int setContentLayout() {
        return R.layout.activity_fast_recyclerview;
    }

    @Override
    public void initView() {
        datas = new ArrayList<>();
        fast_rcv_view = findViewById(R.id.fast_rcv_view);
        fast_rcv_view.setRefreshMode(Mode.BOTH);
        fast_rcv_view.setSpringBackMode(Mode.BOTH);
        fast_rcv_view.setLayoutManager(new LinearLayoutManager(this));
        page_state_view = findViewById(R.id.page_state_view);
    }

    public void setIBaseRecyclerViewCb(IFastRecyclerViewCb iFastRecyclerViewCb) {
        fast_rcv_view.setIBaseRecyclerViewCb(iFastRecyclerViewCb);

    }

    public void refreshComplete() {
        fast_rcv_view.refreshComplete();
        if (CollectionUtils.isNull(datas)) {
            page_state_view.setViewData(State.ERROR);
        } else if (CollectionUtils.isEmpty(datas)) {
            page_state_view.setViewData(State.EMPTY);
        } else {
            page_state_view.setViewData(State.SUCCESS);
        }
    }

    public void updateData(List<T> datas) {
        if (fast_rcv_view.getPageIndex() == 1) {
            this.datas.clear();
        }
        this.datas.addAll(datas);
        fast_rcv_view.updateData(datas);
    }
}
