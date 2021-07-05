package com.micro.user.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Menu {
    @Id
    private Long id;

    private String title;

    private String location;

    private String icon;

    private String sort;

    private String enabled;

    private Long pId;

    @OneToMany(targetEntity = Menu.class)
    private List<Menu> children;
}
