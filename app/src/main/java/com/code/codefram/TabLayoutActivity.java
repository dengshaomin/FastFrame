package com.code.codefram;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.code.cframe.GlobalEvent;
import com.code.cframe.baseview.BaseTitleActivity;
import com.code.cframe.ciface.IBasePresent;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.widget.MsgView;


public class TabLayoutActivity extends BaseTitleActivity implements OnTabSelectListener {

    SlidingTabLayout mTl1;

    SlidingTabLayout mTl2;

    SlidingTabLayout mTl3;

    SlidingTabLayout mTl4;

    SlidingTabLayout mTl5;

    SlidingTabLayout mTl6;

    SlidingTabLayout mTl7;

    SlidingTabLayout mTl8;

    SlidingTabLayout mTl9;

    SlidingTabLayout mTl10;

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
        mTl1 = findViewById(R.id.tl_1);
        mTl2 = findViewById(R.id.tl_2);
        mTl3 = findViewById(R.id.tl_3);
        mTl4 = findViewById(R.id.tl_4);
        mTl5 = findViewById(R.id.tl_5);
        mTl6 = findViewById(R.id.tl_6);
        mTl7 = findViewById(R.id.tl_7);
        mTl8 = findViewById(R.id.tl_8);
        mTl9 = findViewById(R.id.tl_9);
        mTl10 = findViewById(R.id.tl_10);
        mVp = findViewById(R.id.vp);
        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title));
        }

        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
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
    public void eventComming(GlobalEvent globalMsg) {

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

    @Override
    public IBasePresent getPresents() {
        return null;
    }
}
