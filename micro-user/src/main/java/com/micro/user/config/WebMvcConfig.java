package com.micro.user.config;

import com.micro.user.resolver.SearchDataArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * handler method searchData、pageable 形参解析器
     * @param argumentResolvers handler method 形参解析器列表
     */
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // pageable形参解析器
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
        // searchData形参解析器
        argumentResolvers.add(new SearchDataArgumentResolver());
    }
}
