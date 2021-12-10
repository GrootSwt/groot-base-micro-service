package com.micro.base.common.dto.oss;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoDTO implements Serializable {

    private Long id;

    private String fileName;

    private String fileType;

    private String filePath;

    private Long fileSize;

    private String filesId;
}
