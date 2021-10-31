package com.micro.log.controller;

import com.micro.base.common.dto.log.AuditLogDTO;
import com.micro.log.convertor.AuditLogConvertor;
import com.micro.log.model.AuditLog;
import com.micro.log.service.AuditLogService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = {"日志审计"})
@RestController
@RequestMapping(value = "auditLog")
public class AuditLogController {

    @Resource
    private AuditLogService auditLogService;

    @Resource
    private AuditLogConvertor auditLogConvertor;

    @PostMapping(value = "addLog")
    public void addLog(@RequestBody AuditLogDTO auditLogDTO) {
        AuditLog auditLog = auditLogConvertor.toModel(auditLogDTO);
        auditLogService.addLog(auditLog);
    }

    @GetMapping(value = "getView")
    public String getView() {
        return "hello";
    }
}
