package com.micro.oss;

import com.micro.base.common.annotation.EnableMicroBaseWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMicroBaseWeb
public class  OssApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class);
    }
}
