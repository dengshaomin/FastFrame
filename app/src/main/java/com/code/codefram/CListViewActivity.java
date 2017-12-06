package com.code.codefram;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.code.codeframlibrary.commons.listview.CCommonViewHolder;
import com.code.codeframlibrary.commons.listview.CHeaderFooterAdapter;
import com.code.codeframlibrary.commons.ciface.CListCallBackInterface;
import com.code.codeframlibrary.commons.listview.CListView;
import com.github.lazylibrary.util.ToastUtils;

public class CListViewActivity extends AppCompatActivity implements CListCallBackInterface {

    CHeaderFooterAdapter mCHeaderFooterAdapter;

    private List<String> datas;

    private CListView mCListView;

    private TestAdapter mTestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clist_view);
        datas = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            datas.add(i + "");
        }
        mCListView = findViewById(R.id.recycler_view);
        mCListView.setRefreshMode(CListView.BOTH);
        mCListView.setSpringBackMode(CListView.BOTH);
        mCListView.setLayoutManager(new LinearLayoutManager(this));
        mCListView.setCListAction(datas, this);
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
                TestHeaderItem testItem = new TestHeaderItem(CListViewActivity.this);
                testItem.setViewData(headCount + "");
                mCListView.addHeadView(testItem);
                break;
            case R.id.menu_remove_header:
                headCount--;
                mCListView.removeHeadView();
                break;
            case R.id.menu_add_footer:
                footCount++;
                TestHeaderItem testItem1 = new TestHeaderItem(CListViewActivity.this);
                testItem1.setViewData(footCount + "");
                mCListView.addFooterView(testItem1);
                break;
            case R.id.menu_remove_footer:
                footCount--;
                mCListView.removeFooterView();
                break;
        }
        return true;
    }

    @Override
    public void onHeadFootClickLister(View view, Object data, int position) {
        ToastUtils.showToast(this, position + "");
    }

    @Override
    public void onItemClickLister(View view, Object data, int position) {
        ToastUtils.showToast(this, position + "");
    }

    @Override
    public void onRefresh(final int page) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (page == 1) {
                    datas.clear();
                }
                int size = datas.size();
                for (int i = size; i < size + 10; i++) {
                    datas.add(i + "");
                }
                mCListView.updateData(datas);
                mCListView.refreshComplete(CListView.SUCCESS);
            }
        }, 1000);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CCommonViewHolder(new TestItem(this));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TestItem testItem = (TestItem) holder.itemView;
        testItem.setViewData(position + "");
    }
}
