package com.micro.log.schedule;

import com.micro.log.repository.AuditLogHistoryRepository;
import com.micro.log.repository.AuditLogRepository;
import com.micro.log.model.AuditLog;
import com.micro.log.model.AuditLogHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TransferLogTask {

    @Resource
    private AuditLogRepository auditLogRepository;

    @Resource
    private AuditLogHistoryRepository auditLogHistoryRepository;

    /**
     * 获取日志表中所有已解决的日志转移到日志历史表中
     */
    @Scheduled(cron = "0 0 2 1 * *")
    @Transactional(rollbackFor = Exception.class)
    public void transferLog() {
        log.info("日志转移开始");
        List<AuditLog> auditLogs = auditLogRepository.getAllByResolved(true);
        List<AuditLogHistory> auditLogHistories = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        auditLogs.forEach(item -> {
            AuditLogHistory history = new AuditLogHistory();
            BeanUtils.copyProperties(item, history);
            auditLogHistories.add(history);
            ids.add(history.getId());
        });
        auditLogHistoryRepository.saveAll(auditLogHistories);
        auditLogRepository.deleteAllByIdIn(ids);
        log.info("日志转移结束");
    }

}
