package com.code.fastframe.retrofit.models;

import java.io.Serializable;

public class ServerModel<T> implements Serializable {
  public String msg;
  public String code;
  public T data;
  public static final String SUCCESS_CODE = "A00000";
  public boolean success(){
    return SUCCESS_CODE.equals(code);
  }
}
