package com.micro.oss;

import com.micro.base.web.annotation.EnableAuditLog;
import com.micro.base.web.annotation.EnableMicroBaseWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableMicroBaseWeb
@EnableAuditLog
public class  OssApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class);
    }
}
