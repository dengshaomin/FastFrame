package com.code.demo.demo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import com.code.fastframe.baseactivity.BaseTitleActivity;
import com.code.fastframe.baseview.BaseRecyclerView;
import com.code.fastframe.baseview.BaseRecyclerView.Mode;
import com.code.fastframe.baseview.PageStateView;
import com.code.fastframe.baseview.PageStateView.State;
import com.code.fastframe.ciface.IBaseRecyclerViewCb;
import com.code.fastframe.utils.CollectionUtils;
import com.code.fastframe.utils.ToastUtils;
import com.code.demo.R;

public class BaseRecyclerSimpleActivity extends BaseTitleActivity implements IBaseRecyclerViewCb {


    private List<String> datas;

    private BaseRecyclerView baerecycler_view;

    private TestAdapter mTestAdapter;

    private PageStateView page_state_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_clist_view;
    }

    @Override
    public void initView() {
        baerecycler_view = findViewById(R.id.baerecycler_view);
        baerecycler_view.setRefreshMode(BaseRecyclerView.Mode.BOTH);
        baerecycler_view.setSpringBackMode(BaseRecyclerView.Mode.BOTH);
        baerecycler_view.setLayoutManager(new LinearLayoutManager(this));
        baerecycler_view.setIBaseRecyclerViewCb(this);
        mTestAdapter = new TestAdapter(this, datas);
        baerecycler_view.setAdapter(mTestAdapter);
        page_state_view = findViewById(R.id.page_state_view);
    }

    @Override
    public void initData() {
        onRefresh(Mode.START);
    }

    @Override
    public String setTitleText() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void onHeadFootClickLister(View view, Object data, int position) {
        ToastUtils.showToast(position + "");
    }

    @Override
    public void onRefresh(final int state) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> netdata = bornData(state);
                if (!CollectionUtils.isEmpty(netdata)) {
                    if (datas == null) {
                        datas = new ArrayList<>();
                    }
                    if (state == Mode.START) {
                        datas.clear();
                    }
                    datas.addAll(netdata);
                }
                mTestAdapter.updateData(datas);
                baerecycler_view.refreshComplete(true);
                updatePageStateView();
            }
        }, 1000);
    }

    private void updatePageStateView() {
        if (CollectionUtils.isNull(datas)) {
            page_state_view.setViewData(baerecycler_view.getRefreshState() == Mode.START ? State.ERROR : State.SUCCESS);
        } else if (CollectionUtils.isEmpty(datas)) {
            page_state_view.setViewData(baerecycler_view.getRefreshState() == Mode.START ? State.EMPTY : State.SUCCESS);
        } else {
            page_state_view.setViewData(State.SUCCESS);
        }

    }

    private List<String> bornData(int currentState) {
        int length = 0;
        if (currentState != Mode.START) {
            length = CollectionUtils.isEmpty(datas) ? 0 : datas.size();
        }
        List<String> strs = new ArrayList<>();
        for (int i = length; i < length + 10; i++) {
            strs.add(i + "");
        }
        return strs;
    }

    public class TestAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

        private Context mContext;

        private List<T> datas;

        public TestAdapter(Context context, List<T> datas) {
            this.mContext = context;
            this.datas = datas;
        }

        public void updateData(List<T> datas) {
            this.datas = datas;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(new TestItem(mContext)) {
            };
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TestItem testItem = (TestItem) holder.itemView;
            testItem.setViewData(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas == null ? 0 : datas.size();
        }
    }
}
