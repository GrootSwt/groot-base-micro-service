package com.micro.base.web.config;

import com.micro.base.web.bean.ResultData;
import com.micro.base.web.exception.BusinessRuntimeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全局异常处理器
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    /**
     * 全局异常处理器
     *
     * @param e Exception
     * @return Result.failure(异常信息)
     */
    @ExceptionHandler(value = Exception.class)
    public ResultData exceptionHandler(Exception e) {
        e.printStackTrace();
        if (e instanceof BusinessRuntimeException) {
            return ResultData.failure(e.getMessage());
        }
        return ResultData.failure("服务异常！");
    }
}

