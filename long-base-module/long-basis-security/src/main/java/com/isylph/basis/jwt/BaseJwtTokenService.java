package com.isylph.basis.jwt;


import com.isylph.basis.jwt.entities.BaseJwtUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

public interface BaseJwtTokenService<T extends BaseJwtUser> extends JwtTokenService<T> {

    default String getHeaderToken(HttpServletRequest request){
        String token = request.getHeader(HEAD_TOKEN_HEADER);

        if (token == null || !token.startsWith(HEAD_TOKEN_PREFIX)){
            return null;
        }

        if (!StringUtils.isEmpty(token)){
            token = token.replace(HEAD_TOKEN_PREFIX, "");
        }

        return token;
    }

    default void setHeaderToken(HttpServletResponse response, String token){

        // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
        response.setHeader(HEAD_TOKEN_HEADER, HEAD_TOKEN_PREFIX + token);
    }

    default String getHeaderAppId(HttpServletRequest request){
        return request.getHeader(HEAD_APP_ID);
    }

    default String getHeaderSecret(HttpServletRequest request){
        return request.getHeader(HEAD_SECRET_KEY);
    }
    default Long getHeaderUserId(HttpServletRequest request){
        String id = request.getHeader(HEAD_USER_ID);
        if (StringUtils.isEmpty(id)){
            return null;
        }
        return Long.valueOf(id);
    }
    default String getHeaderUserName(HttpServletRequest request){
        return request.getHeader(HEAD_USER_NAME);
    }
    default String getHeaderName(HttpServletRequest request){
        return request.getHeader(HEAD_NAME);
    }
    default String getHeaderUserType(HttpServletRequest request){
        return request.getHeader(HEAD_USER_TYPE);
    }
}
