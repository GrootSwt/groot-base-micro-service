package com.micro.base.web.annotation;

import com.micro.base.web.config.ApplicationRunnerCustom;
import com.micro.base.web.config.GlobalExceptionHandler;
import com.micro.base.web.config.SwaggerConfig;
import com.micro.base.web.config.WebMvcConfig;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {GlobalExceptionHandler.class, SwaggerConfig.class, WebMvcConfig.class, ApplicationRunnerCustom.class})
@EnableSwagger2
public @interface EnableMicroBaseWeb {
}
