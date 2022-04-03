package com.lyj.vblog.handler;

import com.lyj.vblog.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 返回JSON数据
public class MyExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result doException(Exception exception) {
        exception.printStackTrace();
        return Result.fail(-999, "系统异常");
    }
}
