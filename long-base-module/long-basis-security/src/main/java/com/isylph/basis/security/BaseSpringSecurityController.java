package com.isylph.basis.security;

import com.isylph.basis.base.BaseCmd;
import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.basis.jwt.entities.BaseJwtUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class BaseSpringSecurityController {


    public BaseJwtUser getCurrentUserObject(){
        Authentication au = SecurityContextHolder
                .getContext().getAuthentication();
        if (null == au){
            return null;
        }
        Object principal = au.getPrincipal();

        if (principal instanceof BaseJwtUser){
            return ((BaseJwtUser)principal);
        }
        return null;
    }


    public Long getCurrentAccountId(){
        Authentication au = SecurityContextHolder
                .getContext().getAuthentication();
        if (null == au){
            return null;
        }
        Object principal = au.getPrincipal();

        if (principal instanceof BaseJwtUser){
            return ((BaseJwtUser)principal).getId();
        }
        return null;
    }

    public String getCurrentUserName(){
        Authentication au = SecurityContextHolder
                .getContext().getAuthentication();
        if (null == au){
            return null;
        }
        Object principal = au.getPrincipal();

        if (principal instanceof BaseJwtUser){
            return ((BaseJwtUser)principal).getUsername();
        }
        return null;
    }


    public <T extends BaseCmd> void setCurrentAccount(T req){

        Authentication au = SecurityContextHolder
                .getContext().getAuthentication();
        if (null == au){
            return;
        }
        Object principal = au.getPrincipal();

        if (principal instanceof BaseJwtUser jwt){
            log.debug("{}", principal);

            req.setOpAccount(jwt.getUsername());
            req.setOpAccountId(jwt.getId());
            req.setOpType(jwt.getType());
            req.setOpName(jwt.getName());
            return;
        }
        throw new ReturnException(BaseErrorConsts.RET_FORBIDDEN, "未登录");

    }

}
