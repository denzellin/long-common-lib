package com.isylph.security.jwt;

import com.isylph.basis.jwt.BaseJwtTokenService;
import com.isylph.security.beans.SessionUserContextVO;
import com.isylph.utils.json.JacksonUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserTokenAssemblerService implements BaseJwtTokenService<SessionUserContextVO> {

    private static final String SECRET = "ISYLPHPOWERIOT24A202DC4C97A07CE477FB2F86642EA02024";
    private static final String ISSR = "ISYLPHPOWERIOT24A202DC4C97A07CE477FB2F86642EA02024";

    // 过期时间是10天
    private static final long EXPIRATION = 3600L * 24 * 10;

    private static UserTokenAssemblerService userTokenAssemblerService;

    @PostConstruct
    public void init(){
        userTokenAssemblerService = this;
    }

    @Override
    public long getExpiration() {
        return EXPIRATION;
    }

    @Override
    public String getIssr() {
        return ISSR;
    }

    @Override
    public String getSecret() {
        return SECRET;
    }

    @Override
    public Class<SessionUserContextVO> getUserContextClass() {
        return SessionUserContextVO.class;
    }


    public static String createToken(SessionUserContextVO memberVo, List<String> roles) {

        if (null == memberVo){
            memberVo = new SessionUserContextVO();
        }

        return userTokenAssemblerService.createToken(JacksonUtils.serialize(memberVo), roles, memberVo.getExpiredTime());
    }
    public static String createToken(SessionUserContextVO memberVo) {

        if (null == memberVo){
            memberVo = new SessionUserContextVO();
        }

        return userTokenAssemblerService.createToken(JacksonUtils.serialize(memberVo), memberVo.getRoles(), memberVo.getExpiredTime());
    }

    public static void putHeaderToken(HttpServletResponse response, String token){

        userTokenAssemblerService.setHeaderToken(response, token);
    }
}
