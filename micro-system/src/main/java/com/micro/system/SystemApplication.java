package com.micro.system;

import com.micro.base.web.annotation.EnableAuditLog;
import com.micro.base.web.annotation.EnableMicroBaseWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableMicroBaseWeb
@EnableAuditLog
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
