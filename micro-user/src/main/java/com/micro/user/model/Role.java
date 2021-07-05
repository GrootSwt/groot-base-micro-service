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
public class Role {
    @Id
    private Long id;

    private String name;

    private Long roleRelationMenuId;
}
