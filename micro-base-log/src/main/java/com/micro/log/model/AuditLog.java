package com.micro.log.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private String methodName;

    private String simpleClassName;

    private String fullClassName;

    private String requestData;

    private String requestURI;

    private String requestMethod;

    private String responseData;

    private String serviceName;

    private Date createTime;

    /**
     * 请求响应是否成功
     */
    private Boolean success;
}
