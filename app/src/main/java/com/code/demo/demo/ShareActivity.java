package com.code.demo.demo;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;

import com.alibaba.fastjson.JSON;
import com.code.demo.R;
import com.code.fastframe.baseactivity.BaseTitleActivity;
import com.code.fastframe.eventbus.GlobalEvent;
import com.code.fastframe.utils.ToastUtils;
import com.code.runtime.contants.GlobalEventContants;
import com.code.runtime.contants.ShareCotants.Plat;
import com.code.runtime.utils.LoginUtils;
import com.code.runtime.utils.ShareUtils;

public class ShareActivity extends BaseTitleActivity implements OnClickListener {

    View wechart, qqlogin;

    @Override
    public int setContentLayout() {
        return R.layout.activity_share;
    }

    @Override
    public void initView() {
        wechart = findViewById(R.id.wechart);
        wechart.setOnClickListener(this);
        qqlogin = findViewById(R.id.qqlogin);
        qqlogin.setOnClickListener(this);
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
            ShareUtils.share(this, new ShareUtils.Builder().setPlat(plat).setTitle("123").setContent("456").setImageUrl("https://ss3.bdstatic"
                    + ".com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=487628588,2307382441&fm=26&gp=0.jpg").setClickUrl("https://www.baidu.com").build());

        } else if (v == qqlogin) {
            plat = Plat.QQ;
            LoginUtils.login(this, plat);
        }
    }

    @Override
    public boolean needGlobalEvent() {
        return true;
    }

    @Override
    public List<Integer> regeistEvent() {
        return new ArrayList<Integer>() {{
            add(GlobalEventContants.THIRD_PLAT);
        }};
    }

    @Override
    public void eventComming(GlobalEvent globalMsg) {
        super.eventComming(globalMsg);
        ToastUtils.showToast(JSON.toJSONString(globalMsg));
    }
}
