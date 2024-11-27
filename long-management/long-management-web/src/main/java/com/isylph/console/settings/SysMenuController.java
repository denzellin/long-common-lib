package com.isylph.console.settings;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isylph.basis.consts.RetCodeConsts;
import com.isylph.basis.beans.HttpRetData;
import com.isylph.basis.beans.Tree;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.console.api.beans.system.menu.SysMenuQuery;
import com.isylph.console.api.beans.system.menu.SysMenuSaveCmd;
import com.isylph.console.api.beans.system.menu.SysMenuVO;
import com.isylph.console.api.beans.system.menu.SysMenuUpdateCmd;
import com.isylph.console.settings.model.SysMenuPO;
import com.isylph.console.settings.service.SysMenuService;
import com.isylph.controller.CrudController;
import com.isylph.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
@Slf4j
@Tag( name = "100", description = "系统功能")
@RestController
@RequestMapping("/console/menu")
public class SysMenuController
        implements CrudController<
        SysMenuSaveCmd,
        SysMenuUpdateCmd,
        SysMenuQuery,
                SysMenuVO,
                SysMenuPO> {

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public BaseService<SysMenuPO> getService() {
        return sysMenuService;
    }

    @Override
    public SysMenuPO createModel() {
        return new SysMenuPO();
    }

    @Override
    public SysMenuVO createViewObject() {
        return new SysMenuVO();
    }

    private String generateMenuPath (Long fid){
        if (null == fid || 0 == fid) {
            return ".000.";
        }else {
            SysMenuPO fMenu = sysMenuService.getById(fid);
            if (null == fMenu) {
                throw new ReturnException(RetCodeConsts.RET_NOT_FOUND);
            }

            return fMenu.getCode() + fid.toString() + ".";
        }

    }

    @Operation(description = "菜单管理", method = "/**", summary = "200")
    public HttpRetData demo() {
        return HttpRetData.success();
    }

    @Override
    public HttpRetData add(@RequestBody SysMenuSaveCmd request) {

        SysMenuPO pNewItem = new SysMenuPO();
        BeanUtils.copyProperties(request, pNewItem);
        pNewItem.setCode(generateMenuPath(request.getFid()));
        pNewItem.setActive(1);
        pNewItem.setFid(Optional.ofNullable(request.getFid()).orElse(0L));
        sysMenuService.save(pNewItem);
        return HttpRetData.success();
    }

    @Override
    public HttpRetData<List<SysMenuVO>> listByFilter(@RequestBody SysMenuQuery query) {
        Page<SysMenuPO> page = sysMenuService.selectViewListPage(query);
        List<SysMenuVO> vos = new ArrayList<>();
        for (SysMenuPO p: page.getRecords()){
            SysMenuVO v = new SysMenuVO();
            BeanUtils.copyProperties(p, v);
            vos.add(v);
        }

        List<SysMenuVO> list = Tree.assembleTree(vos);

        return HttpRetData.success(list);
    }

    @Transactional
    @Override
    public HttpRetData updateByPrimaryKey(@RequestBody SysMenuUpdateCmd request) {

        SysMenuPO oMenu = sysMenuService.getById(request.getId());
        if (null == oMenu) {
            log.debug("request: {}", request);
            throw new ReturnException(RetCodeConsts.RET_NOT_FOUND);
        }

        String oldPath = oMenu.getCode();

        BeanUtils.copyProperties(request, oMenu);

        oMenu.setCode(generateMenuPath(request.getFid()));
        sysMenuService.updateById(oMenu);

        if (!oldPath.equals(oMenu.getCode())) {
            Long id = oMenu.getId();
            HashMap<String, String> vMap = new HashMap<>();
            vMap.put("old", oldPath + "." + id.toString() );
            vMap.put("new", oMenu.getCode() + "." + id.toString() );
            sysMenuService.batchUpdateCode(vMap);
        }
        return HttpRetData.success();
    }

    @Operation(description = "菜单启用/禁用")
    @PostMapping(value = "/activate")
    public HttpRetData menuActive(@RequestParam(value = "id") Long id,
                                  @RequestParam(value = "active", required = false) Integer active) {

        SysMenuPO oMenu = sysMenuService.getById(id);
        if (null == oMenu) {
            log.debug("failed to get menu: {}", id);
            throw new ReturnException(RetCodeConsts.RET_NOT_FOUND);
        }
        SysMenuPO pNew = new SysMenuPO();
        pNew.setId(id);
        pNew.setActive(active);
        sysMenuService.updateById(pNew);

        return HttpRetData.success();
    }

    @Operation(description = "菜单树")
    @GetMapping(value = "/tree")
    public HttpRetData<List<SysMenuVO>> tree() {
        SysMenuQuery query = new SysMenuQuery();

        Page<SysMenuPO> page = sysMenuService.selectViewListPage(query);
        List<SysMenuVO> vos = new ArrayList<>();
        for (SysMenuPO p: page.getRecords()){
            SysMenuVO v = new SysMenuVO();
            vos.add(v);
        }
        List<SysMenuVO> list = Tree.assembleTree(vos);
        return HttpRetData.success(list);
    }
}

