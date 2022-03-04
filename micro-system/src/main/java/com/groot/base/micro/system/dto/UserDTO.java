package com.groot.base.micro.system.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private String id;

    private String username;

    private String password;

    private String loginName;

    private String phoneNumber;

    private String email;

    private String avatar;

    private String roleId;

    private String roleName;

    private String enabled;
}
