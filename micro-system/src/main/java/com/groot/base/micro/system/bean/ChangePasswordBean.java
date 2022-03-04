package com.groot.base.micro.system.bean;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordBean {

    private String id;

    private String oldPassword;

    private String newPassword;
}
