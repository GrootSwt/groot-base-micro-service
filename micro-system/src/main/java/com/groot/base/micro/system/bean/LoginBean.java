package com.groot.base.micro.system.bean;

import com.groot.base.micro.system.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginBean {

    private String token;

    private User userInfo;

    private String accountName;
}
