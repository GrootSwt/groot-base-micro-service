package com.micro.log.repository;

import com.micro.log.model.AuditLogHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogHistoryRepository extends JpaRepository<AuditLogHistory, Long> {
}
