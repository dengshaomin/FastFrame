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
import com.jpeng.jptabbar.animate.AnimationType;

/**
 * Created by jpeng on 16-11-14.
 */
public class Fragment1 extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup mGroup;

    private JPTabBar mTabBar;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout =inflater.inflate(R.layout.fragment_1,null);
        init(layout);
        return layout;
    }

    private void init(View layout) {
        mTabBar = ((TabBarActivity)getActivity()).getTabbar();
        mGroup = (RadioGroup) layout.findViewById(R.id.radioGroup);
        mGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.radioButton1:
                mTabBar.setAnimation(AnimationType.SCALE);
                break;
            case R.id.radioButton2:
                mTabBar.setAnimation(AnimationType.SCALE2);
                break;
            case R.id.radioButton3:
                mTabBar.setAnimation(AnimationType.JUMP);
                break;
            case R.id.radioButton4:
                mTabBar.setAnimation(AnimationType.FLIP);

                break;
            case R.id.radioButton5:
                mTabBar.setAnimation(AnimationType.ROTATE);
                break;
        }
    }

}
