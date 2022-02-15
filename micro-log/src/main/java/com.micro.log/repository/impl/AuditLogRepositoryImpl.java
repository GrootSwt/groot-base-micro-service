package com.micro.log.repository.impl;

import com.groot.base.common.SearchData;
import com.groot.base.web.repository.BaseRepository;
import com.micro.log.model.AuditLog;
import com.micro.log.model.QAuditLog;
import com.micro.log.repository.AuditLogRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Repository
public class AuditLogRepositoryImpl extends BaseRepository implements AuditLogRepositoryCustom {
    @Override
    protected Class<?> getModelClass() {
        return AuditLog.class;
    }


    @Override
    public Page<AuditLog> pageableSearch(Pageable pageable, SearchData searchData) {
        QAuditLog auditLog = QAuditLog.auditLog;
        BooleanBuilder where = new BooleanBuilder();
        // 请求是否成功
        if (searchData.hasKey("success")) {
            where.and(auditLog.success.eq(searchData.getBooleanValue("success")));
        }
        // 服务名
        if (searchData.hasKey("serviceName")) {
            where.and(auditLog.serviceName.like("%" + searchData.getStringValue("serviceName") + "%"));
        }
        //登录账号
        if (searchData.hasKey("loginName")) {
            where.and(auditLog.loginName.like("%" + searchData.getStringValue("loginName") + "%"));
        }

        if (searchData.hasKey("startDate") && searchData.hasKey("endDate")) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(searchData.getDateValue("endDate"));
            calendar.add(Calendar.DATE, 1);
            Date endDate = calendar.getTime();
            where.and(auditLog.createTime.between(searchData.getDateValue("startDate"), endDate));
        }
        JPAQuery<AuditLog> query = queryFactory.selectFrom(auditLog).where(where);
        return search(query, pageable, auditLog.createTime.desc());
    }
}
