package com.micro.base.web.bean;

import lombok.Data;

/**
 * 操作员信息
 */
@Data
public class OperatorInfo {

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
}
