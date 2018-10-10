package com.code.codefram;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;

import com.code.codeframlibrary.commons.GlobalMsg;
import com.code.codeframlibrary.commons.activity.CListActivity;
import com.code.codeframlibrary.commons.baseview.BaseItemLayout;
import com.code.codeframlibrary.commons.ciface.IBasePresent;
import com.code.codeframlibrary.commons.listview.CListView;
import com.github.lazylibrary.util.ToastUtils;

public class ListWithBaseListActivity extends CListActivity<String> {


    @Override
    public boolean needTitle() {
        return true;
    }

    @Override
    public String setTitleText() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int setTitleLeftImage() {
        return 0;
    }

    @Override
    public int setTitleRightImage() {
        return 0;
    }

    @Override
    public void titleRightClick() {

    }

    @Override
    public List<String> needPermissions() {
        return null;
    }

    @Override
    public void initBundleData() {

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

    }

    @Override
    public void onItemClickLister(View view, Object data, int position) {
        ToastUtils.showToast(this, position + "");
    }

    @Override
    public void onRefresh(int page) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> datas = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    datas.add(i + "");
                }
                updateData(datas);
//                mCListView.refreshComplete(CListView.SUCCESS);  合并
            }
        }, 1000);
    }


    @Override
    public ItemDecoration addItemDecoration() {
        return null;
    }

    @Override
    public LayoutManager setLayoutManager() {
        return null;
    }

    @Override
    public boolean needStateView() {
        return false;
    }

    @Override
    public int setRefreshMode() {
        return CListView.BOTH;
    }

    @Override
    public int setSpringBackMode() {
        return CListView.BOTH;
    }

    @Override
    public BaseItemLayout getListItemView() {
        return new TestItem(this);
    }

    @Override
    public IBasePresent getPresents() {
        return null;
    }
}
