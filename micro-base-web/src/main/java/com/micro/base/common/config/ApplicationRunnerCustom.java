package com.micro.base.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class ApplicationRunnerCustom implements ApplicationRunner {

    @Value(value = "${spring.application.name}")
    private String name;

    @Value(value = "${server.port}")
    private String port;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("**************************************************************");
        log.info("SWAGGER URL: http://localhost:" + port + "/swagger-ui.html");
        log.info("**************************************************************");
    }
}
