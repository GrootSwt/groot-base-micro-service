package com.micro.base.web.aspect;

import com.alibaba.fastjson.JSON;
import com.micro.base.common.dto.log.AuditLogDTO;
import com.micro.base.common.bean.OperatorInfo;
import com.micro.base.web.util.LoginUserInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 全局日志处理
 */
@Aspect
@Slf4j
@Component
public class GlobalAuditLog {

    private final String ADD_LOG_URL = "http://micro-log/auditLog/addLog";

    @Value(value = "${spring.application.name}")
    private String serviceName;

    @Resource
    private MultipartResolver multipartResolver;

    @Resource
    private RestTemplate restTemplate;

    /**
     * 获取切点
     */
    @Pointcut(value = "execution(public * *..controller..*(..))")
    public void pointCut() {
    }

    /**
     * 判断是否进行日志审计
     *
     * @return 是否进行日志审计
     */
    private boolean isAuditLog() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        // 如果为文件操作则不审计日志
        return !multipartResolver.isMultipart(request);
    }

    /**
     * 请求处理成功日志录入
     *
     * @param joinPoint 切入点
     * @param result    响应
     */
    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void doAfterReturning(final JoinPoint joinPoint, Object result) {
        if (isAuditLog()) {
            AuditLogDTO auditLogDTO = getAuditLogDTO(joinPoint);
            auditLogDTO.setResponseData(JSON.toJSONString(result));
            auditLogDTO.setSuccess(true);
            restTemplate.postForEntity(ADD_LOG_URL, auditLogDTO, Void.class);
        }

    }

    /**
     * 异常日志录入
     *
     * @param joinPoint 切入点
     * @param e         异常
     */
    @AfterThrowing(pointcut = "pointCut()", throwing = "e")
    public void doAfterThrowing(final JoinPoint joinPoint, final Throwable e) {
        if (isAuditLog()) {
            AuditLogDTO auditLogDTO = getAuditLogDTO(joinPoint);
            auditLogDTO.setResponseData(JSON.toJSONString(e.getMessage()));
            auditLogDTO.setSuccess(false);
            restTemplate.postForEntity(ADD_LOG_URL, auditLogDTO, Void.class);
        }

    }

    /**
     * 获取日志信息
     *
     * @param joinPoint 切入点
     * @return 日志DTO
     */
    private AuditLogDTO getAuditLogDTO(final JoinPoint joinPoint) {
        AuditLogDTO auditLogDTO = new AuditLogDTO();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        // 服务名
        auditLogDTO.setServiceName(serviceName);
        // 操作人信息
        OperatorInfo operatorInfo = LoginUserInfoUtil.getOperatorInfo();
        if (operatorInfo != null) {
            BeanUtils.copyProperties(operatorInfo, auditLogDTO);
        }
        // 方法名
        String methodName = joinPoint.getSignature().getName();
        auditLogDTO.setMethodName(methodName);
        // 简单类名
        String simpleClassName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        auditLogDTO.setSimpleClassName(simpleClassName);
        // 全类名
        String fullClassName = joinPoint.getSignature().getDeclaringTypeName();
        auditLogDTO.setFullClassName(fullClassName);
        // 请求参数列表
        Object[] requestParams = joinPoint.getArgs();
        // 请求参数列表
        String requestData = JSON.toJSONString(requestParams);
        auditLogDTO.setRequestData(requestData);

        // 请求资源定位符
        String requestURI = request.getRequestURI();
        auditLogDTO.setRequestURI(requestURI);
        // 请求方法
        String requestMethod = request.getMethod();
        auditLogDTO.setRequestMethod(requestMethod);
        // 日志创建时间
        auditLogDTO.setCreateTime(new Date());
        // 操作成功还是失败
        auditLogDTO.setSuccess(true);
        return auditLogDTO;
    }
}
