package com.micro.log.service.impl;

import com.micro.log.model.AuditLog;
import com.micro.log.repository.AuditLogRepository;
import com.micro.log.service.AuditLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Resource
    private AuditLogRepository auditLogRepository;
    @Override
    public void addLog(AuditLog auditLog) {
        auditLogRepository.save(auditLog);
    }
}
