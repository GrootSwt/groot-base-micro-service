package com.groot.base.micro.oss.repository;

import java.util.List;

public interface FileInfoRepositoryCustom {
    List<String> getFileIdListByFilesId(String filesId);
}
