package com.code.codefram;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.code.cframe.activity.FastRecyclerViewActivity;
import com.code.cframe.baseview.BaseRecyclerView.Mode;
import com.code.cframe.utils.CollectionUtils;

public class FastListActivity extends FastRecyclerViewActivity<String> {


    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public String setTitleText() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void getNetData() {
        onRefresh(Mode.START);
    }

    @Override
    public void onRefresh(int currentState) {
        Log.e(this.getClass().getSimpleName(),currentState +"");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                datas.clear();
                updateData(bornData(currentState));
            }
        }, 1000);
    }

    private List<String> bornData(int currentState) {
        int length = 0;
        if (currentState != Mode.START) {
            length = CollectionUtils.isEmpty(fast_rcv_view.getDatas()) ? 0 : fast_rcv_view.getDatas().size();
        }
        List<String> strs = new ArrayList<>();
        for (int i = length; i < length + 10; i++) {
            strs.add(i + "");
        }
        return strs;
    }

    @Override
    public void onItemClickLister(View view, Object data, int position) {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new TestItem(this)) {
        };
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TestItem testItem = (TestItem) holder.itemView;
        testItem.setViewData(fast_rcv_view.getDatas().get(position));
    }
}
