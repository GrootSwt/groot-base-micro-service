package com.micro.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.micro.common.dto.user.UserDTO;
import com.micro.common.util.JwtTokenUtil;
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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
        // 获取token和登录用户信息
        List<HttpCookie> tokenList = cookies.get("token");
        List<HttpCookie> userInfoList = cookies.get("userInfo");

        if (tokenList == null || userInfoList == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        String userInfoStr2 = userInfoList.get(0).getValue();
        String userInfoStr = null;
        try {
            userInfoStr = URLDecoder.decode(userInfoStr2, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        UserDTO userInfo = JSON.parseObject(userInfoStr, UserDTO.class);
        String token = tokenList.get(0).getValue();
        UserDTO userDTOInfo;
        try {
            userDTOInfo = JwtTokenUtil.getUserInfo(token);
        } catch (JwtException e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        // 判断token是否正确
        assert userInfo != null;
        if (userDTOInfo.getLoginName().equals(userInfo.getLoginName())) {

            Date expireDate = JwtTokenUtil.getExpireDate(token);
            Date now = new Date();
            long times = expireDate.getTime() - now.getTime();
            //  更新token
            if (times <= validateTime) {
                String newToken = JwtTokenUtil.generatorToken(userDTOInfo, expireTime);
                response.addCookie(ResponseCookie.from("token", newToken).build());
                // response.addCookie(ResponseCookie.from("userInfo", userInfoStr2).build());
                exchange.mutate().response(response).build();
            }
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
