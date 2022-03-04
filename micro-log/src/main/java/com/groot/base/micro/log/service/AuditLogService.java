package com.groot.base.micro.log.service;

import com.groot.base.common.SearchData;
import com.groot.base.micro.log.model.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditLogService {
    /**
     * 日志新增
     *
     * @param auditLog 日志model
     */
    void addLog(AuditLog auditLog);

    /**
     * 条件分页查询日志
     *
     * @param pageable   分页
     * @param searchData 查询条件
     * @return 符合条件的分页日志
     */
    Page<AuditLog> pageableSearch(Pageable pageable, SearchData searchData);

    /**
     * 批量删除日志
     * @param idArr 日志id数组
     * @return  操作是否成功
     */
    void batchDelete(String[] idArr);
}
