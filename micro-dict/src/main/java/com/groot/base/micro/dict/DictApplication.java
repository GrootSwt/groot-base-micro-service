package com.groot.base.micro.dict;


import com.groot.base.log.annotation.EnableAuditLog;
import com.groot.base.web.annotation.EnableBaseWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBaseWeb
@EnableAuditLog
public class DictApplication {

    public static void main(String[] args) {
        SpringApplication.run(DictApplication.class, args);
    }
}
