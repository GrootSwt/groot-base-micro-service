package com.micro.system.user.model;

import com.groot.base.web.bean.model.BaseModel;
import lombok.*;

import javax.persistence.Entity;

/**
 * 用户
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseModel {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 电话号码
     */
    private String phoneNumber;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 头像id
     */
    private Long avatar;
    /**
     * 启用（0：未启用；1：启用）
     */
    private String enabled;
}
