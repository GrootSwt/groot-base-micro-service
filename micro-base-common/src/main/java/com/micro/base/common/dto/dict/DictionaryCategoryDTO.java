package com.micro.base.common.dto.dict;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class DictionaryCategoryDTO {

    private Long id;

    /**
     * 数据字典分类名称
     */
    private String categoryName;
    /**
     * 数据字典分类key
     */
    private String categoryKey;
    /**
     * 服务名
     */
    private String serviceName;
    /**
     * 数据字典描述
     */
    private String description;
    /**
     * 启用（0：未启用；1：启用）
     */
    private String enabled;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后一次修改时间
     */
    private Date modifyTime;
}
