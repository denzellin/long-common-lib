package com.vivavot.security.jwt;

import com.vivavot.basis.jwt.BaseJwtTokenService;
import com.vivavot.security.beans.SessionUserContextVO;
import com.vivavot.utils.json.JacksonUtils;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserTokenAssemblerService extends BaseJwtTokenService<SessionUserContextVO> {

    // 过期时间是10天
    long EXPIRATION = 3600L * 24 * 10;

    @Override
    default Class<SessionUserContextVO> getUserContextClass() {
        return SessionUserContextVO.class;
    }

    @Override
    default long getExpiration() {
        return EXPIRATION;
    }

    default String createToken(SessionUserContextVO memberVo, List<String> roles) {

        if (null == memberVo){
            memberVo = new SessionUserContextVO();
        }

        return createToken(JacksonUtils.serialize(memberVo), roles, memberVo.getExpiredTime());
    }
    default String createToken(SessionUserContextVO memberVo) {

        if (null == memberVo){
            memberVo = new SessionUserContextVO();
        }

        return createToken(JacksonUtils.serialize(memberVo), memberVo.getRoles(), memberVo.getExpiredTime());
    }

    default void putHeaderToken(HttpServletResponse response, String token){

        setHeaderToken(response, token);
    }
}
