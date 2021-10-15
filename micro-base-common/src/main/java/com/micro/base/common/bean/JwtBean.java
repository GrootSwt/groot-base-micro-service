package com.micro.base.common.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtBean {

    private String key;

    private String expireTime;

    private String validateTime;
}
