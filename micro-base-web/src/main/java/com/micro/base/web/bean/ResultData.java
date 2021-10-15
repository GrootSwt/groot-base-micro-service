package com.micro.base.web.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 返回类
 */
@Getter
@Setter
@ToString
public class ResultData {

    private static final String SUCCESS = "success";

    private static final String FAILURE = "failure";
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


    public ResultData() {

    }

    public ResultData(String status) {
        this.status = status;
    }

    public ResultData(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResultData(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResultData success() {
        return new ResultData(ResultData.SUCCESS);
    }

    public static ResultData success(String message) {
        return new ResultData(ResultData.SUCCESS, message);
    }

    public static ResultData success(String message, Object data) {
        return new ResultData(ResultData.SUCCESS, message, data);
    }

    public static ResultData failure() {
        return new ResultData(ResultData.FAILURE);
    }

    public static ResultData failure(String message) {
        return new ResultData(ResultData.FAILURE, message);
    }

    public static ResultData failure(String message, Object data) {
        return new ResultData(ResultData.FAILURE, message, data);
    }
}
