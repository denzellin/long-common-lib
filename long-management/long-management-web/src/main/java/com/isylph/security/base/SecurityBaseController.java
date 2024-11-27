package com.isylph.security.base;

import com.isylph.basis.consts.CommonConsts;
import com.isylph.basis.consts.RetCodeConsts;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.basis.jwt.beans.BaseJwtUser;
import com.isylph.basis.security.BaseSpringSecurityController;
import com.isylph.console.api.beans.system.role.SysRoleVO;
import com.isylph.security.beans.SessionUserContextVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SecurityBaseController extends BaseSpringSecurityController {

    protected SessionUserContextVO getCurrentContext(){

        BaseJwtUser principal = getCurrentUserObject();

        if (principal instanceof SessionUserContextVO){
            log.info("{}", principal);
            return ((SessionUserContextVO) principal);
        }
        log.error("failed to get member info");
        throw new ReturnException(RetCodeConsts.LOGIN_TIMEOUT);
    }



    protected String getCurrentAccount(){

        return getCurrentContext().getUsername();

    }

    private List<SysRoleVO> getRoles(){
        List<SysRoleVO> roles = getCurrentContext().getRoleList();
        if(CollectionUtils.isEmpty(roles)){
            return null;
        }

        return roles;
    }

    protected List<Long> getCurrentAccountRoles(){

        List<SysRoleVO> roles = getRoles();

        List<Long> ids = new ArrayList<>();
        for(SysRoleVO item: roles){
            ids.add(item.getId());
        }
        return ids;

    }
    protected Boolean isSuperAadmin(){
        List<SysRoleVO> roles = getRoles();

        for(SysRoleVO item: roles){
            if (CommonConsts.ROLE_ID_ADMIN == item.getId() ){
                return true;
            }
        }

        return false;
    }
}
