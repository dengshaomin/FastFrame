package com.code.demo.demo;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.code.fastframe.baseactivity.BaseTitleActivity;
import com.code.fastframe.utils.ToastUtils;
import com.code.demo.R;
import com.jpeng.jptabbar.BadgeDismissListener;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;


public class TabBarActivity extends BaseTitleActivity implements BadgeDismissListener, OnTabSelectListener {

    ViewPager viewPager;

    JPTabBar tabbar;

    Fragment0 mTab0;

    Fragment1 mTab1;

    Fragment2 mTab2;

    Fragment3 mTab3;

    Fragment4 mTab4;

    @Titles
    private static final String[] mTitles = {"页面一", "页面二", "页面三", "页面四"};

    @SeleIcons
    private static final int[] mSeleIcons = {R.mipmap.tab1_selected, R.mipmap.tab2_selected, R.mipmap.tab3_selected,
            R.mipmap.tab4_selected};

    @NorIcons
    private static final int[] mNormalIcons = {R.mipmap.tab1_normal, R.mipmap.tab2_normal, R.mipmap.tab3_normal, R
            .mipmap.tab4_normal};

    private List<Fragment> list = new ArrayList<>();

    @Override
    public String setTitleText() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_tab_bar;
    }

    @Override
    public void initView() {
        viewPager = findViewById(R.id.view_pager);
        tabbar = findViewById(R.id.tabbar);
        mTab0 = new Fragment0();
        mTab1 = new Fragment1();
        mTab2 = new Fragment2();
        mTab3 = new Fragment3();
        mTab4 = new Fragment4();
        tabbar.setTabListener(this);
        list.add(mTab0);
        list.add(mTab1);
        list.add(mTab2);
        list.add(mTab3);
//        list.add(mTab4);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), list, mTitles));
        tabbar.setContainer(viewPager);
        tabbar.setDismissListener(this);
        //显示圆点模式的徽章
        //设置容器
        tabbar.showBadge(0, 50);
        //设置Badge消失的代理
        tabbar.setTabListener(this);
        tabbar.setUseScrollAnimate(true);
        tabbar.getMiddleView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("中间点击");
            }
        });
    }

    @Override
    public void onDismiss(int position) {
        if (position == 0) {
            mTab0.clearCount();
        }
    }


    @Override
    public void onTabSelect(int index) {

    }


    public JPTabBar getTabbar() {
        return tabbar;
    }

    @Override
    public boolean needStatuBarTransparent() {
        return true;
    }
}
