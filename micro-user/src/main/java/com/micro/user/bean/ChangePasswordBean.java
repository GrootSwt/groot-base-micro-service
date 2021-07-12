package com.micro.user.bean;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordBean {
    private String oldPassword;

    private String newPassword;
}
