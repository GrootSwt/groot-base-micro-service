package com.micro.base.web.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuditLog {

    @Pointcut(value = "execution(public * *..controller..*(..))")
    public void pointCut() {
    }

    @Value(value = "${spring.application.name}")
    private String serviceName;

    @Resource
    private MultipartResolver multipartResolver;


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

    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void doAfterReturning(final JoinPoint joinPoint, Object result) {

    }

    @AfterThrowing(
            pointcut = "pointCut()",
            throwing = "e"
    )
    public void doAfterThrowing(final JoinPoint joinPoint, final Throwable e) {

    }
}
