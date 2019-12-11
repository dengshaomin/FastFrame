package com.code.demo.demo;

import android.view.View;
import android.view.View.OnClickListener;

import com.code.demo.R;
import com.code.fastframe.baseactivity.BaseTitleActivity;
import com.code.runtime.utils.ShareUtils;
import com.code.runtime.utils.ShareUtils.Plat;

public class ShareActivity extends BaseTitleActivity implements OnClickListener {

    View wechart;

    @Override
    public int setContentLayout() {
        return R.layout.activity_share;
    }

    @Override
    public void initView() {
        wechart = findViewById(R.id.wechart);
        wechart.setOnClickListener(this);
    }

    @Override
    public String setTitleText() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        int plat = 0;
        if (v == wechart) {
            plat = Plat.WECHART;
        }
        ShareUtils.share(this, new ShareUtils.Builder().setPlat(plat).setTitle("123").setContent("456").setImageUrl("https://ss3.bdstatic"
                + ".com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=487628588,2307382441&fm=26&gp=0.jpg").setClickUrl("https://www.baidu.com").build());
    }
}
