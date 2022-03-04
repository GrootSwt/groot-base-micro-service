package com.groot.base.micro.log.mq;

import com.groot.base.log.bean.AuditLogBean;
import com.groot.base.micro.log.service.AuditLogService;
import com.groot.base.micro.log.model.AuditLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 日志接收
 */
@Slf4j
@Component
public class LogReceiver {

    @Resource
    private AuditLogService auditLogService;

    @RabbitHandler
    @RabbitListener(queues = {"micro-log"})
    public void process(AuditLogBean auditLogBean) {
        AuditLog auditLog = new AuditLog();
        BeanUtils.copyProperties(auditLogBean, auditLog);
        auditLogService.addLog(auditLog);
    }
}
