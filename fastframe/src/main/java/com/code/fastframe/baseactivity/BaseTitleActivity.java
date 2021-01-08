package com.code.fastframe.baseactivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.code.fastframe.R;
import com.code.fastframe.ciface.IBaseLayout;
import com.code.fastframe.ciface.ITitle;
import com.gyf.immersionbar.ImmersionBar;

/**
 * Created by dengshaomin on 2017/11/7.
 */

public abstract class BaseTitleActivity extends BaseBundleActivity implements IBaseLayout, ITitle {

  protected ViewGroup container;

  private View left_image, right_image, nav_status_space;

  private TextView title_text;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_title_base);
    initTitle();
    this.initView();
    this.initData();
    this.getNetData();
  }

  public void getNetData() {
  }

  @Override
  public void initData() {

  }

  private void initTitle() {
    container = findViewById(R.id.container);
    if (setContentLayout() != 0) {
      LayoutInflater.from(this).inflate(setContentLayout(), container);
    }
    title_text = findViewById(R.id.title_text);
    left_image = findViewById(R.id.left_image);
    right_image = findViewById(R.id.right_image);
    nav_status_space = findViewById(R.id.nav_status_space);
    left_image.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        titleLeftClick();
      }
    });
    right_image.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        titleRightClick();
      }
    });
    title_text.setText(TextUtils.isEmpty(setTitleText()) ? "" : setTitleText());
    if (setTitleLeftImage() > 0) {
      left_image.setBackgroundResource(setTitleLeftImage());
    }
    if (setTitleRightImage() > 0) {
      left_image.setBackgroundResource(setTitleRightImage());
    }
    if (needImmerse()) {
      ViewGroup.LayoutParams layoutParams = nav_status_space.getLayoutParams();
      layoutParams.height = ImmersionBar.getStatusBarHeight(this);
      nav_status_space.setLayoutParams(layoutParams);
      ImmersionBar.with(this).init();
    }
  }

  @Override
  public void titleLeftClick() {
    onBackPressed();
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

  @Override public boolean needImmerse() {
    return false;
  }
}
