package com.groot.base.micro.oss.service;

import com.groot.base.micro.oss.model.FileInfo;
import com.groot.base.web.exception.BusinessRuntimeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface FileOperationService {
    List<FileInfo> upload(HttpServletRequest request);

    void download(String id, HttpServletResponse response) throws BusinessRuntimeException, IOException;

    FileInfo getFileInfoById(String id) throws BusinessRuntimeException;

    List<FileInfo> listFileInfoByIdArr(String[] idArr);

    void deleteFileById(String id) throws BusinessRuntimeException;

    void deleteFileListByIdArr(String[] idArr);

    List<FileInfo> getFileListByFilesId(String filesId);

    List<String> getFileIdListByFilesId(String filesId);

    void deleteFilesByFilesId(String filesId);
}
