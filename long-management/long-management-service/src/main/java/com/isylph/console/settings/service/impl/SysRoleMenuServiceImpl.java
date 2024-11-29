package com.isylph.console.settings.service.impl;

import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.consts.CommonConsts;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.console.api.beans.system.menu.SysMenuVO;
import com.isylph.console.api.beans.system.role.SysRoleMenuQuery;
import com.isylph.console.settings.dao.SysRoleMenuMapper;
import com.isylph.console.settings.model.SysMenuPO;
import com.isylph.console.settings.model.SysRoleMenuPO;
import com.isylph.console.settings.model.SysRolePO;
import com.isylph.console.settings.service.SysMenuService;
import com.isylph.console.settings.service.SysRoleMenuService;
import com.isylph.console.settings.service.SysRoleService;
import com.isylph.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class SysRoleMenuServiceImpl
        extends BaseServiceImpl<
                SysRoleMenuMapper,
                SysRoleMenuPO,
        SysRoleMenuQuery>
        implements SysRoleMenuService {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public List<SysMenuVO> listRoleMenu(Long roleId) {

        return baseMapper.listRoleMenu(roleId);
    }

    @Override
    public List<SysMenuVO> listRolesMenus(List<Long> ids) {
        for(Long id: ids){
            if (CommonConsts.ROLE_ID_ADMIN == id){
                return baseMapper.listRolesSysMenus();
            }
        }
        return baseMapper.listRolesMenus(ids);
    }

    @Transactional
    @Override
    public void saveRoleMenu(Long roleId, List<Long> menuIds) {
        log.debug("req: {}, ids:{}",roleId, menuIds);

        SysRolePO rolePo = sysRoleService.getById(roleId);
        if (null  == rolePo ){
            log.error("the role is not exist: {}", roleId);
            throw new ReturnException(BaseErrorConsts.RET_NOT_FOUND);
        }

        baseMapper.deleteByRoleId(roleId);
        if (CollectionUtils.isEmpty(menuIds)){
            return;
        }

        List<SysRoleMenuPO> menus = new ArrayList<>();
        for (Long uid : menuIds) {
            SysMenuPO menu = sysMenuService.getById(uid);
            if (null == menu) {
                log.info("ignore the invalid menu: {}", uid);
                continue;
            }


            SysRoleMenuPO item = new SysRoleMenuPO();
            item.setRoleId(roleId);
            item.setMenuId(uid);
            menus.add(item);
        }

        baseMapper.insert(menus);

    }
}
