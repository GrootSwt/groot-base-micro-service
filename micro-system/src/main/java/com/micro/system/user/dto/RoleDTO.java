package com.micro.system.user.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String enabled;

    private Long[] menuIdArr;
}
