package com.micro.oss.model;

import com.groot.base.web.bean.model.BaseModel;
import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * 文件信息
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FileInfo extends BaseModel implements Serializable {

    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 多文件id
     */
    private String filesId;
}
