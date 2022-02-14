package com.micro.system.log.mq;

import com.groot.base.log.bean.AuditLogBean;
import com.micro.system.log.model.AuditLog;
import com.micro.system.log.service.AuditLogService;
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
    @RabbitListener(queues = {"single-blog"})
    public void process(AuditLogBean auditLogBean) {
        AuditLog auditLog = new AuditLog();
        BeanUtils.copyProperties(auditLogBean, auditLog);
        auditLogService.addLog(auditLog);
    }
}
