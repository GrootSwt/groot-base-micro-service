package com.micro.user.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoleRelationMenu {
    @Id
    private Long id;

    private Long roleId;

    private Long menuId;
}
