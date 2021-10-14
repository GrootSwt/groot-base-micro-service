package com.micro.oss.repository;

import com.micro.oss.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

    FileInfo findFirstById(Long id);
}
