package com.micro.swt.repository;

import com.micro.swt.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

    FileInfo findFirstById(Long id);
}
