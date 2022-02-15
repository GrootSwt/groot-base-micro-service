package com.micro.log.service.impl;


import com.groot.base.common.SearchData;
import com.micro.log.repository.AuditLogRepository;
import com.micro.log.service.AuditLogService;
import com.micro.log.model.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Resource
    private AuditLogRepository auditLogRepository;

    @Override
    public void addLog(AuditLog auditLog) {
        auditLogRepository.save(auditLog);
    }

    @Override
    public Page<AuditLog> pageableSearch(Pageable pageable, SearchData searchData) {
        return auditLogRepository.pageableSearch(pageable, searchData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(Long[] idArr) {
        auditLogRepository.deleteAllByIdIn(Arrays.asList(idArr));
    }
}
