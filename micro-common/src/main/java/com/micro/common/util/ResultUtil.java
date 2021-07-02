package com.micro.common.util;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回值工具类
 */
@Getter
@Setter
public class ResultUtil {
    /**
     * 状态
     */
    private String status;
    /**
     * 信息
     */
    private String message;
    /**
     * 数据
     */
    private Object data;


    public ResultUtil() {

    }

    public ResultUtil(String status) {
        this.status = status;
    }

    public ResultUtil(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResultUtil(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResultUtil success() {
        return new ResultUtil("success");
    }

    public static ResultUtil success(String message) {
        return new ResultUtil("success", message);
    }

    public static ResultUtil success(String message, Object data) {
        return new ResultUtil("success", message, data);
    }

    public static ResultUtil failure() {
        return new ResultUtil("failure");
    }

    public static ResultUtil failure(String message) {
        return new ResultUtil("failure", message);
    }

    public static ResultUtil failure(String message, Object data) {
        return new ResultUtil("failure", message, data);
    }
}
