package com.code.codefram;

import com.code.cframe.activity.FastRecyclerViewActivity;

public class ListWithBaseListActivity extends FastRecyclerViewActivity<String> {


    @Override
    public String setTitleText() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void getNetData() {

    }
}
