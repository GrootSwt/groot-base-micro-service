package com.micro.gateway.filter;

import com.micro.common.dto.user.UserDTO;
import com.micro.common.util.JwtTokenUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 登录校验器
 */
@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String uri = request.getURI().toString();
        //  登录请求放行
        if (uri.contains("login")) {
            return chain.filter(exchange);
        }
        // 获取Cookies
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        if (cookies.isEmpty()) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        // 获取token和登录名
        List<HttpCookie> tokenList = cookies.get("token");
        List<HttpCookie> loginNameList = cookies.get("loginName");

        if (tokenList == null || loginNameList == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        String loginName = loginNameList.get(0).getValue();
        String token = tokenList.get(0).getValue();
        UserDTO userDTOInfo;
        try {
            userDTOInfo = JwtTokenUtil.getUserInfo(token);
        }catch (JwtException e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        // 判断token是否正确
        if (userDTOInfo.getLoginName().equals(loginName)) {
            return chain.filter(exchange);
        } else {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
