package com.micro.log.service;

import com.micro.log.model.AuditLog;

public interface AuditLogService {
    void addLog(AuditLog auditLog);
}
