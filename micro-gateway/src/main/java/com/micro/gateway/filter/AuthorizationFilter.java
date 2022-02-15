package com.micro.gateway.filter;

import com.micro.gateway.bean.Whitelist;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 登录校验器
 */
@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${groot.login.expire-time}")
    private Long expireTime;

    @Resource
    private Whitelist whitelist;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String uri = request.getURI().toString();
        //  白名单请求放行
        for (String s : whitelist.getWhitelist()) {
            if (uri.contains(s)) {
                return chain.filter(exchange);
            }
        }

        // 获取Cookies
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        if (cookies.isEmpty()) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        // 获取token
        List<HttpCookie> tokenList = cookies.get("token");

        // token不存在，提示未授权
        if (tokenList == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        // 校验token
        String token = tokenList.get(0).getValue();

        // 获取accountName
        List<HttpCookie> accountNameList = cookies.get("accountName");
        if (accountNameList == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        String accountName = accountNameList.get(0).getValue();

        String redisToken = stringRedisTemplate.opsForValue().get(token);
        if (redisToken != null) {
            if (redisToken.equals(accountName)) {
                Long expire = stringRedisTemplate.getExpire(token, TimeUnit.MINUTES);
                if (expire != null && expire < 5L) {
                    stringRedisTemplate.opsForValue().set(token, redisToken, expireTime, TimeUnit.MINUTES);
                }
                return chain.filter(exchange);
            }
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
