package com.znq.freedom.intercept;

import org.springframework.http.MediaType;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.znq.freedom.utils.Result;

// 如果引入了swagger或knife4j的文档生成组件，这里需要仅扫描自己项目的包，否则文档无法正常生成
@RestControllerAdvice(basePackages = "com.znq.freedom")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
  @Override
  // supports：判断是否要交给 beforeBodyWrite 方法执行，true：需要；false：不需要
  public boolean supports(MethodParameter returnType, 
                          Class<? extends HttpMessageConverter<?>> converterType) {
    // 如果不需要进行封装的，可以添加一些校验手段，比如添加标记排除的注解
    return true;
  }

  @Override
  // beforeBodyWrite：对 response 进行具体的处理
  public Object beforeBodyWrite(Object body, 
                                MethodParameter returnType, 
                                MediaType selectedContentType, 
                                Class<? extends HttpMessageConverter<?>> selectedConverterType, 
                                ServerHttpRequest request, 
                                ServerHttpResponse response) {
    // 提供一定的灵活度，如果body已经被包装了，就不进行包装
    if (body instanceof Result) {
      return body;
    }
    return Result.success(body);
  }
}