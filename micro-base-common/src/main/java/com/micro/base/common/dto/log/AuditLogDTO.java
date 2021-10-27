package com.micro.base.common.dto.log;

import lombok.Data;

import java.util.Date;

@Data
public class AuditLogDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 类名
     */
    private String simpleClassName;

    /**
     * 全类名
     */
    private String fullClassName;

    /**
     * 请求参数
     */
    private String requestData;

    /**
     * 请求路径
     */
    private String requestURI;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 响应数据、异常数据
     */
    private String responseData;

    /**
     * 服务名
     */
    private String serviceName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 请求响应是否成功
     */
    private Boolean success;
}