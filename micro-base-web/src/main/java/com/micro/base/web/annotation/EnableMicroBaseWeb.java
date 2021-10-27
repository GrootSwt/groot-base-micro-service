package com.micro.base.web.annotation;

import com.micro.base.web.config.*;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {GlobalExceptionHandler.class, WebMvcConfig.class, ApplicationRunnerCustom.class, SwaggerConfig.class, RestTemplateConfig.class})
@EnableSwagger2
public @interface EnableMicroBaseWeb {
}
