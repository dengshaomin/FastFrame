package com.code.codefram.demo;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.code.cframe.baseactivity.BaseTitleActivity;
import com.code.cframe.lazyviewpager.LazyFragmentPagerAdapter;
import com.code.cframe.lazyviewpager.LazyViewPager;
import com.code.cframe.lazyviewpager.LazyViewPagerAdapter;
import com.code.codefram.R;

public class LazyViewPagerActivity extends BaseTitleActivity {
    private LazyViewPager vp_fragment,vp_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_lazy_view_pager;
    }

    @Override
    public void initView() {
        vp_fragment = findViewById(R.id.vp_fragment);
        vp_view = findViewById(R.id.vp_view);
        vp_fragment.setAdapter(new CustomLazyFragmentPagerAdapter(getSupportFragmentManager()));
        vp_view.setAdapter(new CustomLazyViewPagerAdapter(this));
        vp_fragment.setInitLazyItemOffset(1);
    }

    @Override
    public String setTitleText() {
        return null;
    }


    private static class CustomLazyFragmentPagerAdapter extends LazyFragmentPagerAdapter {

        private CustomLazyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(ViewGroup container, int position) {
            return CustomFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 6;
        }

    }

    public static class CustomFragment extends Fragment implements LazyFragmentPagerAdapter.Laziable {

        private static final String KEY_POSITION = "position";

        private static CustomFragment newInstance(int position) {
            CustomFragment customFragment = new CustomFragment();
            Bundle args = new Bundle(1);
            args.putInt(KEY_POSITION, position);
            customFragment.setArguments(args);
            return customFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return buildItemView(getArguments().getInt(KEY_POSITION));
        }

        private View buildItemView(int position) {
            TextView view = new TextView(getActivity());
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            view.setText(String.format("ItemFragment #%d", position));
            view.setTextColor(Color.BLACK);
            view.setTextSize(18);
            view.setGravity(Gravity.CENTER);
            view.setBackgroundColor(Color.GREEN);
            return view;
        }
    }

    private static class CustomLazyViewPagerAdapter extends LazyViewPagerAdapter {

        private final Context mContext;

        private CustomLazyViewPagerAdapter(Context context) {
            mContext = context;
        }

        @Override
        public View getItem(ViewGroup container, int position) {
            return buildItemView(position);
        }

        @Override
        public int getCount() {
            return 4;
        }

        private View buildItemView(int position) {
            TextView view = new TextView(mContext);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            view.setText(String.format("ItemView #%d", position));
            view.setTextColor(Color.BLACK);
            view.setTextSize(18);
            view.setGravity(Gravity.CENTER);
            view.setBackgroundColor(Color.YELLOW);
            return view;
        }

    }

}
