package com.micro.base.web.annotation;

import com.micro.base.web.aspect.GlobalAuditLog;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {GlobalAuditLog.class})
public @interface EnableAuditLog {
}
