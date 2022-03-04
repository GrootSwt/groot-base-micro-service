package com.groot.base.micro.log.repository;

import com.groot.base.micro.log.model.AuditLogHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogHistoryRepository extends JpaRepository<AuditLogHistory, String> {
}
