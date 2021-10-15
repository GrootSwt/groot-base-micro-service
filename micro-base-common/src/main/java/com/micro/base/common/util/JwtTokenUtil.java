package com.micro.base.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.micro.base.common.bean.JwtBean;
import com.micro.base.common.dto.user.UserDTO;
import io.jsonwebtoken.*;
import org.joda.time.DateTime;

import java.util.Date;
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
        JSONObject micro = JSON.parseObject(JSON.toJSONString(map.get("micro")));
        JwtBean jwtBean = JSON.parseObject(JSON.toJSONString(micro.getJSONObject("jwt")), JwtBean.class);
        return jwtBean.getKey();
    }

    /**
     * 生成token
     *
     * @param userDTOInfo 用户信息
     * @param expire      过期时间
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

    /**
     * 获取过期时间
     *
     * @param token token
     * @return 过期时间
     */
    public static Date getExpireDate(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        return body.getExpiration();
    }
}
