package com.isylph.basis.jwt;


import com.isylph.basis.jwt.beans.BaseJwtUser;
import com.isylph.utils.json.JacksonUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BaseJwtTokenService<T extends BaseJwtUser> {

    String TOKEN_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
    String TOKEN_APP_ID = "app-id";
    String TOKEN_SECRET_KEY = "secret-key";


    String ROLE_CLAIMS = "user_role";

    default long getExpiration(){
        return  0;
    }
    default String getIssr(){
        return null;
    }

    default String getSecret(){
        return null;
    }


    default String createToken(String jsonMember, List<String> roles, Long expiration) {
        Map<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, StringUtils.join(roles, ","));
        if (null == jsonMember){
            jsonMember = "";
        }


        byte[] keyBytes = Decoders.BASE64.decode(getSecret());
        Key key = Keys.hmacShaKeyFor(keyBytes);

        long expire = expiration == null ? getExpiration() : expiration;
        return Jwts.builder()
                // 这里要早set一点，放到后面会覆盖别的字段
                .setClaims(map)
                .setIssuer(getIssr())
                .setSubject(jsonMember)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expire * 1000))
                .signWith(key)
                .compact();
    }

    // 获取用户角色
    default List<String> getUserRole(String token){
        String roles = (String)getTokenBody(token).get(ROLE_CLAIMS);
        return  Arrays.asList(StringUtils.split(roles, ","));
    }


    //从token中获取用户名
    default String getUserString(String token){

        Claims claims = getTokenBody(token);
        if (claims == null || isExpiration(claims)){
            throw new SignatureException("the token is expired");
        }

        return claims.getSubject();
    }

    Class <T> getUserContextClass();

    //从token中获取用户名
    default T getUser(String token){
        Class <T> entityClass = getUserContextClass();
        return JacksonUtils.deserialize(getUserString(token), entityClass);
    }


    //是否已过期
    default boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }

    default boolean isExpiration(Claims claims ){
        return claims.getExpiration().before(new Date());
    }

    default Claims getTokenBody(String token){

        Claims claims;
        try {
            claims = Jwts.parserBuilder().setSigningKey(getSecret()).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }


        return claims;
    }

    default String getHeaderToken(HttpServletRequest request){
        String token = request.getHeader(TOKEN_HEADER);

        if (token == null || !token.startsWith(TOKEN_PREFIX)){
            return null;
        }

        if (!StringUtils.isEmpty(token)){
            token = token.replace(TOKEN_PREFIX, "");
        }

        return token;
    }

    default void setHeaderToken(HttpServletResponse response, String token){

        // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
        response.setHeader(TOKEN_HEADER, TOKEN_PREFIX + token);
    }

    default String getHeaderAppId(HttpServletRequest request){
        return request.getHeader(TOKEN_APP_ID);
    }

    default String getHeaderSecret(HttpServletRequest request){
        return request.getHeader(TOKEN_SECRET_KEY);
    }
}
