package com.isylph.console.settings.service.impl;

import com.isylph.basis.consts.RetCodeConsts;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.console.api.beans.system.func.SysFuncVO;
import com.isylph.console.api.beans.system.role.SysRoleVO;
import com.isylph.console.api.beans.system.role.SysRoleFuncQuery;
import com.isylph.console.settings.dao.SysRoleFuncMapper;
import com.isylph.console.settings.model.SysFuncPO;
import com.isylph.console.settings.model.SysRoleFuncPO;
import com.isylph.console.settings.model.SysRolePO;
import com.isylph.console.settings.service.SysFuncService;
import com.isylph.console.settings.service.SysRoleFuncService;
import com.isylph.console.settings.service.SysRoleService;
import com.isylph.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
@Slf4j
@Service
public class SysRoleFuncServiceImpl
        extends BaseServiceImpl<
                SysRoleFuncMapper,
                SysRoleFuncPO,
        SysRoleFuncQuery>
        implements SysRoleFuncService {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysFuncService sysFuncService;

    @Override
    public void saveRoleFunc(Long roleId, List<Long> funcIds) {

        log.debug("req: {}, ids:{}",roleId, funcIds);

        SysRolePO rolePo = sysRoleService.getById(roleId);
        if (null  == rolePo ){
            log.error("the role is not exist: {}", roleId);
            throw new ReturnException(RetCodeConsts.RET_NOT_FOUND);
        }

        baseMapper.deleteByRoleId(roleId);

        if(CollectionUtils.isEmpty(funcIds)){
            return;
        }

        List<SysRoleFuncPO>  funcPOS = new ArrayList<>();
        for(Long id: funcIds){
            SysFuncPO func = sysFuncService.getById(id);
            if (null == func){
                log.info("ignore the invalid functions: {}", id);
                continue;
            }


            SysRoleFuncPO item = new SysRoleFuncPO();
            item.setRoleId(roleId);
            item.setFuncId(id);
            funcPOS.add(item);
        }

        baseMapper.insert(funcPOS);
    }

    @Override
    public List<SysFuncVO> listRolesFuncs(List<Long> ids) {
        return baseMapper.listRolesFuncs(ids);
    }

    @Override
    public List<SysFuncVO> listRoleFunc(Long roleId) {
        return baseMapper.listRoleFunc(roleId);
    }

    @Override
    public List<SysFuncVO> listAllFunc() {
        return baseMapper.listAllFunc();
    }

    @Override
    public List<SysRoleVO> listRolesByFunc(Long funcId) {
        return baseMapper.listRolesByFunc(funcId);
    }
}
