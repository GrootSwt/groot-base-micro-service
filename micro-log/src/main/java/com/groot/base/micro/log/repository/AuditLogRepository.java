package com.groot.base.micro.log.repository;

import com.groot.base.micro.log.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, String>, AuditLogRepositoryCustom {
    /**
     * 根据id列表批量删除
     *
     * @param id id列表
     */
    void deleteAllByIdIn(Collection<String> id);

    List<AuditLog> getAllByResolved(Boolean resolved);
}
