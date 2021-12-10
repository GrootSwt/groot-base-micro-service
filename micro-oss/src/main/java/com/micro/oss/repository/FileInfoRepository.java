package com.micro.oss.repository;

import com.micro.oss.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> ,FileInfoRepositoryCustom{

    FileInfo findFirstById(Long id);

    List<FileInfo> findAllByFilesId(String filesId);

    void deleteAllByFilesId(String filesId);

}
