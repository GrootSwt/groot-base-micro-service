package com.micro.log.service.impl;

import com.micro.base.common.bean.ResultData;
import com.micro.base.common.bean.SearchData;
import com.micro.log.model.AuditLog;
import com.micro.log.repository.AuditLogRepository;
import com.micro.log.service.AuditLogService;
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
    public ResultData batchDelete(Long[] idArr) {
        auditLogRepository.deleteAllByIdIn(Arrays.asList(idArr));
        return ResultData.success("日志删除成功！");
    }
}
