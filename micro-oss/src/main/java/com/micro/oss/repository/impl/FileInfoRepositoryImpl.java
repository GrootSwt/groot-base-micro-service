package com.micro.oss.repository.impl;

import com.micro.base.web.repository.BaseRepository;
import com.micro.oss.model.FileInfo;
import com.micro.oss.model.QFileInfo;
import com.micro.oss.repository.FileInfoRepositoryCustom;

import java.util.List;

public class FileInfoRepositoryImpl extends BaseRepository implements FileInfoRepositoryCustom {
    @Override
    protected Class<?> getModelClass() {
        return FileInfo.class;
    }


    @Override
    public List<Long> getFileIdListByFilesId(String filesId) {
        QFileInfo fileInfo = QFileInfo.fileInfo;
        return queryFactory.select(fileInfo.id).from(fileInfo).where(fileInfo.filesId.eq(filesId)).fetch();
    }
}
