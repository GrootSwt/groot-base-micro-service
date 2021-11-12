package com.micro.log.repository;

import com.micro.log.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long>, AuditLogRepositoryCustom {

    void deleteAllByIdIn(Collection<Long> id);
}
