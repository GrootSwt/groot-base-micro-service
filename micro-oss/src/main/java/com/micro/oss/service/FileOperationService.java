package com.micro.oss.service;

import com.micro.base.web.exception.BusinessRuntimeException;
import com.micro.oss.model.FileInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface FileOperationService {
    List<FileInfo> upload(HttpServletRequest request);

    void download(Long id, HttpServletResponse response) throws BusinessRuntimeException, IOException;

    FileInfo getFileInfoById(Long id) throws BusinessRuntimeException;

    List<FileInfo> listFileInfoByIdArr(Long[] idArr);

    void deleteFileById(Long id) throws BusinessRuntimeException;

    void deleteFileListByIdArr(Long[] idArr);
}
