package com.micro.common.util;

import com.alibaba.fastjson.JSON;
import com.micro.common.dto.user.UserDTO;
import io.jsonwebtoken.*;
import org.joda.time.DateTime;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * jwt工具类
 */
public class JwtTokenUtil {
    /**
     * 从配置文件中获取key
     *
     * @return key
     */
    public static String getKey() {
        Map<String, Object> map = YamlUtil.getYamlByPath("");
        Map jwtKey = JSON.parseObject(JSON.toJSONString(map.get("jwt")), Map.class);
        return jwtKey.get("key").toString();
    }

    /**
     * 生成token
     *
     * @param userDTOInfo 用户信息
     * @param expire   过期时间
     * @return token
     */
    public static String generatorToken(UserDTO userDTOInfo, Integer expire) {
        return Jwts.builder().claim("userInfo", userDTOInfo)
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.HS256, getKey()).compact();
    }

    /**
     * 根据token获取用户信息
     *
     * @param token token
     * @return 用户信息
     */
    public static UserDTO getUserInfo(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        String userInfo = JSON.toJSONString(body.get("userInfo", LinkedHashMap.class));
        return JSON.parseObject(userInfo, UserDTO.class);
    }
}
