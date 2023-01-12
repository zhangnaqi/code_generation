package com.znq.freedom.utils;

//常用结果的枚举
public enum ResultEnum {
  SUCCESS(2001, "接口调用成功"),
  VALIDATE_FAILED(2002, "参数校验失败"),
  COMMON_FAILED(2003, "接口调用失败"),
  FORBIDDEN(2004, "没有权限访问资源");

  private Integer code;
  private String message;

  private ResultEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
  public Integer getCode() {
    return code;
  }
  public void setCode(Integer code) {
    this.code = code;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  
}