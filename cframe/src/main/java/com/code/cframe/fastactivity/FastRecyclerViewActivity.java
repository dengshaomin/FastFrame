package com.code.cframe.fastactivity;

import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.code.cframe.R;
import com.code.cframe.baseactivity.BaseTitleActivity;
import com.code.cframe.baseview.BaseRecyclerView.Mode;
import com.code.cframe.baseview.PageStateView;
import com.code.cframe.baseview.PageStateView.State;
import com.code.cframe.ciface.IFastRecyclerViewCb;
import com.code.cframe.recyclerview.FastRecyclerView;
import com.code.cframe.utils.CollectionUtils;
/**
 * 快速创建列表页面
 * */
public abstract class FastRecyclerViewActivity<T> extends BaseTitleActivity implements IFastRecyclerViewCb {

    protected FastRecyclerView fast_rcv_view;

    protected PageStateView page_state_view;

    @Override
    public int setContentLayout() {
        return R.layout.activity_fast_recyclerview;
    }

    @Override
    public void initView() {
        fast_rcv_view = findViewById(R.id.fast_rcv_view);
        fast_rcv_view.setRefreshMode(Mode.BOTH);
        fast_rcv_view.setSpringBackMode(Mode.BOTH);
        fast_rcv_view.setLayoutManager(new LinearLayoutManager(this));
        page_state_view = findViewById(R.id.page_state_view);
        fast_rcv_view.setIBaseRecyclerViewCb(this);
    }

    @Override
    public void onHeadFootClickLister(View view, Object data, int position) {

    }

    public void updateData(List<T> datas) {
        fast_rcv_view.updateData(datas);
        updatePageStateView();
    }

    private void updatePageStateView() {
        if (CollectionUtils.isNull(fast_rcv_view.getDatas())) {
            page_state_view.setViewData(fast_rcv_view.getRefreshState() == Mode.START ? State.ERROR : State.SUCCESS);
        } else if (CollectionUtils.isEmpty(fast_rcv_view.getDatas())) {
            page_state_view.setViewData(fast_rcv_view.getRefreshState() == Mode.START ? State.EMPTY : State.SUCCESS);
        } else {
            page_state_view.setViewData(State.SUCCESS);
        }

    }
}
