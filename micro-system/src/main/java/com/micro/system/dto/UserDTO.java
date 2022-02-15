package com.micro.system.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String loginName;

    private String phoneNumber;

    private String email;

    private Long avatar;

    private Long roleId;

    private String roleName;

    private String enabled;
}
