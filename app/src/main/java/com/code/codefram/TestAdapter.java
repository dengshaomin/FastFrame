package com.code.codefram;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.code.cframe.listview.CCommonViewHolder;

/**
 * Created by dengshaomin on 2017/12/4.
 */

public class TestAdapter<T> extends RecyclerView.Adapter<CCommonViewHolder> {

    private Context mContext;

    private List<T> datas;

    public TestAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @Override
    public CCommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CCommonViewHolder(new TestItem(mContext));
    }

    @Override
    public void onBindViewHolder(CCommonViewHolder holder, int position) {
        TestItem testItem = (TestItem) holder.itemView;
        testItem.setViewData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }
}
