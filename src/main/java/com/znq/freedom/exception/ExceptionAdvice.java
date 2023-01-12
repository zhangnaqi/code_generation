package com.znq.freedom.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.znq.freedom.utils.Result;

//统一拦截异常
@RestControllerAdvice(basePackages = "com.znq.freedom")
public class ExceptionAdvice {

  /**
   * 顶级异常捕获并统一处理，当其他异常无法处理时候选择使用
   */
  @ExceptionHandler({Exception.class})
  public Result<?> handle(Exception ex) {
    return Result.failed(ex.getMessage());
  }

}