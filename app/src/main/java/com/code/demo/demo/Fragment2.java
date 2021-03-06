package com.code.demo.demo;

import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.code.demo.R;
import com.jpeng.jptabbar.JPTabBar;

/**
 * Created by jpeng on 16-11-14.
 */
public class Fragment2 extends Fragment implements RadioGroup.OnCheckedChangeListener{
    JPTabBar mTabBar;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_2,null);
        init(layout);
        return layout;
    }

    /**
     * 初始化
     */
    private void init(View layout) {
        mTabBar = ((TabBarActivity)getActivity()).getTabbar();

        ((RadioGroup)layout.findViewById(R.id.radioGroup1)).setOnCheckedChangeListener(this);
        ((RadioGroup)layout.findViewById(R.id.radioGroup2)).setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.radioButton1:
                mTabBar.setUseScrollAnimate(true);
                break;

            case R.id.radioButton2:
                mTabBar.setUseScrollAnimate(false);
                break;

            case R.id.radioButton3:
                mTabBar.setUseFilter(true);
                break;
            case R.id.radioButton4:
                mTabBar.setUseFilter(false);
                break;
        }
    }
}
