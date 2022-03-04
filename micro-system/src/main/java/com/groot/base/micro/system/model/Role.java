package com.groot.base.micro.system.model;

import com.groot.base.web.bean.model.BaseModel;
import lombok.*;

import javax.persistence.Entity;

/**
 * 角色
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role extends BaseModel {

    /**
     * 角色名
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 启用
     */
    private String enabled;

}
