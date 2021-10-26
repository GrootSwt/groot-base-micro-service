package com.micro.gateway.filter;

import com.micro.base.common.dto.user.UserDTO;
import com.micro.base.common.util.JwtTokenUtil;
import com.micro.gateway.bean.Whitelist;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 登录校验器
 */
@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {

    @Value(value = "${micro.jwt.expireTime}")
    private Integer expireTime;

    @Value(value = "${micro.jwt.validateTime}")
    private Long validateTime;

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
        UserDTO userInfo;
        try {
            userInfo = JwtTokenUtil.getUserInfo(token);
        } catch (JwtException e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }

        // 判断是否需要更新token过期时间
        Date expireDate = JwtTokenUtil.getExpireDate(token);
        Date now = new Date();
        long times = expireDate.getTime() - now.getTime();
        //  更新token
        if (times <= validateTime) {
            String newToken = JwtTokenUtil.generatorToken(userInfo, expireTime);
            response.addCookie(ResponseCookie.from("token", newToken).build());
            exchange.mutate().response(response).build();
        }
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
