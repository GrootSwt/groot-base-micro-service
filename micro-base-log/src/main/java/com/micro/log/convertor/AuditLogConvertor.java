package com.micro.log.convertor;

import com.micro.base.web.convertor.BaseConvertor;
import com.micro.base.common.dto.log.AuditLogDTO;
import com.micro.log.model.AuditLog;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AuditLogConvertor extends BaseConvertor<AuditLog, AuditLogDTO> {
    @Override
    public AuditLog toModel(AuditLogDTO auditLogDTO) {
        AuditLog model = new AuditLog();
        BeanUtils.copyProperties(auditLogDTO, model);
        return model;
    }

    @Override
    public AuditLogDTO toDTO(AuditLog auditLog) {
        AuditLogDTO dto = new AuditLogDTO();
        BeanUtils.copyProperties(auditLog, dto);
        return dto;
    }
}
