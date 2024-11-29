package com.isylph.console.settings.service.impl;

import com.isylph.basis.consts.CommonConsts;
import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.console.api.beans.system.role.SysRoleUserVO;
import com.isylph.console.api.beans.system.role.SysRoleVO;
import com.isylph.console.api.beans.system.role.SysRoleUserQuery;
import com.isylph.console.settings.model.SysRoleUserPO;
import com.isylph.console.settings.service.SysRoleService;
import com.isylph.console.settings.service.SysRoleUserService;
import com.isylph.console.settings.dao.SysRoleUserMapper;
import com.isylph.console.settings.model.SysRolePO;
import com.isylph.operator.api.beans.operator.SysOperatorVO;
import com.isylph.operator.application.OperatorApplicationService;
import com.isylph.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
public class SysRoleUserServiceImpl
        extends BaseServiceImpl<
                SysRoleUserMapper,
                SysRoleUserPO,
        SysRoleUserQuery>
        implements SysRoleUserService {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private OperatorApplicationService operatorApplicationService;

    @Override
    public List<SysRoleVO> getUserRoles(Long userId) {
        /**
         * 超级管理员
         * */
        if(1 == userId){
            SysRolePO po =  sysRoleService.getById(CommonConsts.ROLE_ID_ADMIN);
            SysRoleVO vo = new SysRoleVO();
            BeanUtils.copyProperties(po, vo);
            return Collections.singletonList(vo);
        }

        List<SysRoleVO> roles = baseMapper.getUserRoles(userId);

        /**
         * 普通用户都属于member组
         * */
        SysRolePO po =  sysRoleService.getById(CommonConsts.ROLE_ID_MEMBER);
        SysRoleVO member = new SysRoleVO();
        BeanUtils.copyProperties(po, member);
        if(null == roles){
            roles = new ArrayList<>();
        }
        roles.add(member);
        return roles;
    }

    @Override
    public List<SysOperatorVO> listUserByRoleId(Long roleId) {
        List<SysRoleUserVO> vs = baseMapper.listUserByRoleId(roleId);
        if (CollectionUtils.isEmpty(vs)){
            return null;
        }

        return vs.stream().map(v->{
            return operatorApplicationService.getOperator(v.getUserId());
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void saveRoleMember(Long roleId, List<Long> userIds) {
        if(CollectionUtils.isEmpty(userIds)){
            return;
        }

        SysRolePO role  = sysRoleService.getById(roleId);

        //判断是否默认角色-不能修改
        if (role == null){
            throw new ReturnException(BaseErrorConsts.RET_BAD_PARAM);
        }

        baseMapper.deleteByRoleId(roleId);

        for (Long id: userIds){
            addRoleMember(roleId, id);
        }

    }

    @Override
    public void addRoleMember(Long roleId, Long userId) {
        SysOperatorVO user = operatorApplicationService.getOperator(userId);
        if (user != null){
            SysRoleUserPO roleUser = new SysRoleUserPO();
            roleUser.setRoleId(roleId);
            roleUser.setUserId(userId);
            baseMapper.insert(roleUser);
        }
    }
}
