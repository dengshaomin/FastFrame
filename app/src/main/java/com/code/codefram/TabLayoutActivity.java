package com.code.codefram;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.code.codeframlibrary.commons.GlobalMsg;
import com.code.codeframlibrary.commons.baseview.BaseTitleActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.widget.MsgView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabLayoutActivity extends BaseTitleActivity implements OnTabSelectListener {

    @BindView(R.id.tl_1)
    SlidingTabLayout mTl1;

    @BindView(R.id.tl_2)
    SlidingTabLayout mTl2;

    @BindView(R.id.tl_3)
    SlidingTabLayout mTl3;

    @BindView(R.id.tl_4)
    SlidingTabLayout mTl4;

    @BindView(R.id.tl_5)
    SlidingTabLayout mTl5;

    @BindView(R.id.tl_6)
    SlidingTabLayout mTl6;

    @BindView(R.id.tl_7)
    SlidingTabLayout mTl7;

    @BindView(R.id.tl_8)
    SlidingTabLayout mTl8;

    @BindView(R.id.tl_9)
    SlidingTabLayout mTl9;

    @BindView(R.id.tl_10)
    SlidingTabLayout mTl10;

    @BindView(R.id.vp)
    ViewPager mVp;

    private Context mContext = this;

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private final String[] mTitles = {
            "热门", "iOS", "Android"
            , "前端", "后端", "设计", "工具资源"
    };

    private FragmentAdapter mAdapter;

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
    public int setContentLayout() {
        return R.layout.activity_tab_layout;
    }

    @Override
    public void initView() {
        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title));
        }

        View decorView = getWindow().getDecorView();
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments,mTitles);
        mVp.setAdapter(mAdapter);

        mTl1.setViewPager(mVp);
        mTl2.setViewPager(mVp);
        mTl3.setOnTabSelectListener(this);
        mTl3.setViewPager(mVp);
        mTl4.setViewPager(mVp);
        mTl5.setViewPager(mVp);
        mTl6.setViewPager(mVp);
        mTl7.setViewPager(mVp, mTitles);
        mTl8.setViewPager(mVp, mTitles, this, mFragments);
        mTl9.setViewPager(mVp);
        mTl10.setViewPager(mVp);

        mVp.setCurrentItem(4);

        mTl1.showDot(4);
        mTl3.showDot(4);
        mTl2.showDot(4);

        mTl2.showMsg(3, 5);
        mTl2.setMsgMargin(3, 0, 10);
        MsgView rtv_2_3 = mTl2.getMsgView(3);
        if (rtv_2_3 != null) {
            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }

        mTl2.showMsg(5, 5);
        mTl2.setMsgMargin(5, 0, 10);
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
    public void onTabSelect(int position) {
        Toast.makeText(mContext, "onTabSelect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselect(int position) {
        Toast.makeText(mContext, "onTabReselect&position--->" + position, Toast.LENGTH_SHORT).show();
    }
}
