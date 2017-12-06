package com.code.codeframlibrary.commons.ciface;

/**
 * Created by dengshaomin on 2017/11/8.
 */

public interface ITitle {
    public boolean needTitle();

    public int setTitleLeftImage();

    public int setTitleRightImage();

    public String setTitleText();

    public void titleLeftClick();
    public void titleRightClick();
}
