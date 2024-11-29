package com.isylph.security.jwt;

import com.isylph.basis.jwt.BaseJwtTokenService;
import com.isylph.security.beans.SessionUserContextVO;
import com.isylph.utils.json.JacksonUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
