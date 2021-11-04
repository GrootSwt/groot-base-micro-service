package com.micro.user.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 菜单、权限
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 菜单、权限名
     */
    private String title;
    /**
     * 地址
     */
    private String location;
    /**
     * 图标
     */
    private String icon;
    /**
     * 排序
     */
    private String sort;
    /**
     * 启用（0：未启用；1：启用）
     */
    private String enabled;
    /**
     * 父菜单id
     */
    private Long parentId;
    /**
     * 类型（1：菜单；2：权限）
     */
    private String type;
}
