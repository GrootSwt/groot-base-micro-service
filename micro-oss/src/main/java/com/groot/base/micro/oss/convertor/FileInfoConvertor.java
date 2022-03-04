package com.groot.base.micro.oss.convertor;

import com.groot.base.web.convertor.BaseConvertor;
import com.groot.base.micro.oss.dto.FileInfoDTO;
import com.groot.base.micro.oss.model.FileInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class FileInfoConvertor extends BaseConvertor<FileInfo, FileInfoDTO> {


    @Override
    public FileInfo toModel(FileInfoDTO fileInfoDTO) {
        FileInfo fileInfo = new FileInfo();
        BeanUtils.copyProperties(fileInfoDTO, fileInfo);
        return fileInfo;
    }

    @Override
    public FileInfoDTO toDTO(FileInfo fileInfo) {
        FileInfoDTO fileInfoDTO = new FileInfoDTO();
        BeanUtils.copyProperties(fileInfo, fileInfoDTO);
        return fileInfoDTO;
    }
}
