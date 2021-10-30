package com.micro.log.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity(name = "audit_log")
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

    /**
     * 请求方法名
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
     * 请求数据
     */
    private String requestData;

    /**
     * 请求路径
     */
    @Column(name = "request_uri")
    private String requestURI;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 响应数据
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
