package com.code.codefram;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.code.cframe.activity.BaseTitleActivity;
import com.code.cframe.baseview.BaseRecyclerView;
import com.code.cframe.baseview.BaseRecyclerView.Mode;
import com.code.cframe.baseview.PageStateView;
import com.code.cframe.baseview.PageStateView.State;
import com.code.cframe.ciface.IBaseRecyclerViewCb;
import com.code.cframe.utils.ToastUtils;

public class BaseRecyclerViewActivity extends BaseTitleActivity implements IBaseRecyclerViewCb {


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hf_add_remove, menu);
        return true;
    }

    private int headCount;

    private int footCount;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_header:
                headCount++;
                TestHeaderItem testItem = new TestHeaderItem(BaseRecyclerViewActivity.this);
                testItem.setViewData(headCount + "");
                baerecycler_view.addHeadView(testItem);
                break;
            case R.id.menu_remove_header:
                headCount--;
                baerecycler_view.removeHeadView();
                break;
            case R.id.menu_add_footer:
                footCount++;
                TestHeaderItem testItem1 = new TestHeaderItem(BaseRecyclerViewActivity.this);
                testItem1.setViewData(footCount + "");
                baerecycler_view.addFooterView(testItem1);
                break;
            case R.id.menu_remove_footer:
                footCount--;
                baerecycler_view.removeFooterView();
                break;
        }
        return true;
    }

    @Override
    public void onHeadFootClickLister(View view, Object data, int position) {
        ToastUtils.showToast(this, position + "");
    }

    @Override
    public void onRefresh(final int state) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                datas.clear();
                for (int i = 0; i < 10; i++) {
                    datas.add(i + "");
                }
                mTestAdapter.notifyDataSetChanged();
                page_state_view.setViewData(State.SUCCESS);
                baerecycler_view.refreshComplete();
            }
        }, 1000);
    }

    public class TestAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

        private Context mContext;

        private List<T> datas;

        public TestAdapter(Context context, List<T> datas) {
            this.mContext = context;
            this.datas = datas;
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
