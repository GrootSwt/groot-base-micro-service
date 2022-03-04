package com.groot.base.micro.dict.model;

import com.groot.base.web.bean.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
public class Dictionary extends BaseModel {

    /**
     * 数据字典key
     */
    private String dictionaryKey;
    /**
     * 数据字典value
     */
    private String dictionaryValue;
    /**
     * 启用状态（0：未启用；1：已启用）
     */
    private String enabled;
    /**
     * 描述
     */
    private String description;
    /**
     * 数据字典分类id
     */
    private String categoryId;
    /**
     * 父级数据字典id
     */
    private String parentId;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后一次修改时间
     */
    private Date modifyTime;
}
