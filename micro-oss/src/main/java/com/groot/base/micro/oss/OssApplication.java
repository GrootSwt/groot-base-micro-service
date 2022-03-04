package com.groot.base.micro.oss;

import com.groot.base.web.annotation.EnableBaseWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBaseWeb
public class  OssApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class);
    }
}
