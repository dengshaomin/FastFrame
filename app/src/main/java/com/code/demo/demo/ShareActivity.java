package com.code.demo.demo;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.code.demo.R;
import com.code.fastframe.baseactivity.BaseTitleActivity;
import com.code.fastframe.eventbus.GlobalEvent;
import com.code.fastframe.utils.RealPathFromUriUtils;
import com.code.fastframe.utils.ToastUtils;
import com.code.runtime.contants.ComponentsContants.Action;
import com.code.runtime.contants.GlobalEventContants;
import com.code.runtime.contants.Result;
import com.code.runtime.contants.ShareContants;
import com.code.runtime.contants.ShareContants.Plat;
import com.code.runtime.contants.ShareContants.Type;
import com.code.runtime.factory.UserInfoFactory;
import com.code.runtime.models.events.ThirdPlatEventBean;
import com.code.runtime.utils.LoginUtils;
import com.code.runtime.utils.ShareUtils;

public class ShareActivity extends BaseTitleActivity implements OnClickListener {

    View wechartlogin, qqlogin, share;

    TextView qq_info;

    RadioGroup share_type;

    String shareLocalPath;

    @Override
    public int setContentLayout() {
        return R.layout.activity_share;
    }

    @Override
    public void initView() {
        wechartlogin = findViewById(R.id.wechartlogin);
        wechartlogin.setOnClickListener(this);
        qqlogin = findViewById(R.id.qqlogin);
        qqlogin.setOnClickListener(this);
        qq_info = findViewById(R.id.qq_info);
        share = findViewById(R.id.share);
        share.setOnClickListener(this);
        share_type = findViewById(R.id.share_type);
        share_type.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.share_type_image) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 1);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //获取选中文件的定位符
            Uri uri = data.getData();
            if (uri != null) {
                shareLocalPath = RealPathFromUriUtils.getRealPathFromUri(this,uri);
            }
        }
    }

    @Override
    public String setTitleText() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        if (v == wechartlogin) {
            LoginUtils.login(this, Plat.WECHART);
        } else if (v == qqlogin) {
            LoginUtils.login(this, Plat.QQ);
        } else if (v == share) {
            if (share_type.getCheckedRadioButtonId() == R.id.share_type_text_image) {
                ShareUtils.share(this, new ShareUtils.Builder().setType(Type.IMAGE_TEXT).setTitle("123").setContent("456").setImageUrl("https"
                        + "://ss3"
                        + ".bdstatic"
                        + ".com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=487628588,2307382441&fm=26&gp=0.jpg").setClickUrl("https://www.baidu.com").build());
            } else if (share_type.getCheckedRadioButtonId() == R.id.share_type_image) {
                if (TextUtils.isEmpty(shareLocalPath)) {
                    ToastUtils.showToast("选择本地图片");
                    return;
                }
                shareLocalPath = shareLocalPath.replace("content://", "");
                ShareUtils.share(this, new ShareUtils.Builder().setType(Type.IMAGE)
                        .setImageUrl(shareLocalPath).build());
            }
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
        if (globalMsg.getMsgId() == GlobalEventContants.THIRD_PLAT) {
            ThirdPlatEventBean thirdPlatEventBean = (ThirdPlatEventBean) globalMsg.getData();
            if (thirdPlatEventBean.plat == Plat.QQ) {
                if (TextUtils.equals(thirdPlatEventBean.type, Action.Login)) {
                    if (thirdPlatEventBean.result == Result.CALCEL) {
                        ToastUtils.showToast("用户取消登录");
                    } else if (thirdPlatEventBean.result == Result.FAIL) {
                        ToastUtils.showToast("登录失败");
                    } else {
                        ToastUtils.showToast("登录成功");
                        qq_info.setText(JSON.toJSONString(UserInfoFactory.getInstance().mQQUserInfoBean));
                    }
                } else if (TextUtils.equals(thirdPlatEventBean.type, Action.Share)) {
                    if (thirdPlatEventBean.result == Result.CALCEL) {
                        ToastUtils.showToast("用户取消分享");
                    } else if (thirdPlatEventBean.result == Result.FAIL) {
                        ToastUtils.showToast("分享失败");
                    } else {
                        ToastUtils.showToast("分享成功");
                        qq_info.setText(JSON.toJSONString(UserInfoFactory.getInstance().mQQUserInfoBean));
                    }
                }
            }
        }
    }
}
