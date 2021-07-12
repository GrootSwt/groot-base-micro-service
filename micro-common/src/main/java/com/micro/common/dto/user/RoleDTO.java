package com.micro.common.dto.user;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private Long id;

    private String name;

    private String description;

    private String enabled;
}
