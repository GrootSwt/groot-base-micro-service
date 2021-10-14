package com.micro.base.common.annotation;

import com.micro.base.common.config.ApplicationRunnerCustom;
import com.micro.base.common.config.GlobalExceptionHandler;
import com.micro.base.common.config.SwaggerConfig;
import com.micro.base.common.config.WebMvcConfig;
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
