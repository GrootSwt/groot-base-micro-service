package com.micro.user.exception;

import com.micro.common.util.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    /**
     * 全局异常处理器
     * @param e Exception
     * @return  Result.failure(异常信息)
     */
    @ExceptionHandler(value = Exception.class)
    public ResultUtil exceptionHandler(Exception e) {
        e.printStackTrace();
        return ResultUtil.failure(e.getMessage());
    }
}

