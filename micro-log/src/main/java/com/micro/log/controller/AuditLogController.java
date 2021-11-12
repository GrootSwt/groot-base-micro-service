package com.micro.log.controller;

import com.micro.base.common.bean.ResultData;
import com.micro.base.common.bean.SearchData;
import com.micro.base.common.dto.log.AuditLogDTO;
import com.micro.log.convertor.AuditLogConvertor;
import com.micro.log.model.AuditLog;
import com.micro.log.service.AuditLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @ApiOperation(value = "日志新增")
    @PostMapping(value = "addLog")
    public void addLog(@RequestBody AuditLogDTO auditLogDTO) {
        AuditLog auditLog = auditLogConvertor.toModel(auditLogDTO);
        auditLogService.addLog(auditLog);
    }

    @ApiOperation(value = "日志分页条件查询")
    @GetMapping(value = "pageableSearch")
    public ResultData pageableSearch(Pageable pageable, SearchData searchData) {
        Page<AuditLog> auditLogs = auditLogService.pageableSearch(pageable, searchData);
        Page<AuditLogDTO> auditLogDTOS = auditLogConvertor.toPageDTO(auditLogs);
        return ResultData.success("获取日志成功！", auditLogDTOS);
    }

    @ApiOperation(value = "日志批量删除")
    @DeleteMapping(value = "batchDelete")
    public ResultData batchDelete(@RequestParam Long[] idArr) {
        return auditLogService.batchDelete(idArr);
    }
}
