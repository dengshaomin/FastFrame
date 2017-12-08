package com.code.codefram;

import java.util.List;

import android.os.Bundle;

import com.code.codeframlibrary.commons.GlobalMsg;
import com.code.codeframlibrary.commons.baseview.BaseTitleActivity;
import com.code.codeframlibrary.commons.utils.CImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CircleImageViewActivity extends BaseTitleActivity {

    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;

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
        return R.layout.activity_circle_image_view;
    }

    @Override
    public void initView() {
        CImageUtils.getInstance().loadImage(mProfileImage,"http://img2.imgtn.bdimg.com/it/u=2450994032,3525797548&fm=214&gp=0.jpg");
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
    public void eventComming(GlobalMsg globalMsg) {

    }

    @Override
    public void setViewData(Object data) {

    }
}
