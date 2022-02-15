package com.micro.log.repository;

import com.micro.log.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long>, AuditLogRepositoryCustom {
    /**
     * 根据id列表批量删除
     *
     * @param id id列表
     */
    void deleteAllByIdIn(Collection<Long> id);

    List<AuditLog> getAllByResolved(Boolean resolved);
}
