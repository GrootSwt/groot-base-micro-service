package com.groot.base.micro.oss.repository.impl;


import com.groot.base.web.repository.BaseRepository;
import com.groot.base.micro.oss.model.FileInfo;
import com.groot.base.micro.oss.model.QFileInfo;
import com.groot.base.micro.oss.repository.FileInfoRepositoryCustom;

import java.util.List;

public class FileInfoRepositoryImpl extends BaseRepository implements FileInfoRepositoryCustom {
    @Override
    protected Class<?> getModelClass() {
        return FileInfo.class;
    }


    @Override
    public List<String> getFileIdListByFilesId(String filesId) {
        QFileInfo fileInfo = QFileInfo.fileInfo;
        return queryFactory.select(fileInfo.id).from(fileInfo).where(fileInfo.filesId.eq(filesId)).fetch();
    }
}
