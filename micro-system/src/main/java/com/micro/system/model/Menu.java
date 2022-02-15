package com.micro.system.model;

import com.groot.base.web.bean.model.BaseModel;
import lombok.*;

import javax.persistence.Entity;

/**
 * 菜单、权限
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Menu extends BaseModel {

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
