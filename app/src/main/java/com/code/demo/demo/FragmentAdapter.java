package com.code.demo.demo;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jpeng on 16-11-14.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    private String[] pageTitles;

    public FragmentAdapter(FragmentManager fm, List<Fragment> list, String[] pageTitles) {
        super(fm);
        this.list = list;
        this.pageTitles = pageTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles == null ? "" : pageTitles[position];
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
