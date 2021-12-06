package com.micro.base.common.dto.system;

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
