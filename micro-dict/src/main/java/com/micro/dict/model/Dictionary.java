package com.micro.dict.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 数据字典名称
     */
    private String dictionaryName;
    /**
     * 数据字典key
     */
    private String key;
    /**
     * 数据字典value
     */
    private String value;
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
