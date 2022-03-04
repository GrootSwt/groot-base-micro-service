package com.groot.base.micro.system;

import com.groot.base.log.annotation.EnableAuditLog;
import com.groot.base.web.annotation.EnableBaseWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBaseWeb
@EnableAuditLog
@EnableScheduling
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
