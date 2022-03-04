package com.groot.base.micro.system.model;

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
    private String roleId;
    /**
     * 菜单、权限id
     */
    private String menuId;
}
