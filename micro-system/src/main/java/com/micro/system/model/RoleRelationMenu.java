package com.micro.system.model;

import com.groot.base.web.bean.model.BaseModel;
import lombok.*;

import javax.persistence.Entity;

/**
 * 角色关联菜单、权限
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoleRelationMenu extends BaseModel {

    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 菜单、权限id
     */
    private Long menuId;
}
