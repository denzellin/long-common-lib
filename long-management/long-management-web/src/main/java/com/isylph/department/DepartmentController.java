package com.isylph.department;


import com.isylph.basis.base.RetPage;
import com.isylph.basis.beans.HttpRetData;
import com.isylph.basis.beans.Tree;
import com.isylph.basis.security.BaseSpringSecurityController;
import com.isylph.department.api.beans.DepartmentQuery;
import com.isylph.department.api.beans.DepartmentSaveCmd;
import com.isylph.department.api.beans.DepartmentUpdateCmd;
import com.isylph.department.api.beans.DepartmentVO;
import com.isylph.department.application.DepartmentApplicationService;
import com.isylph.security.beans.SessionUserContextVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 部门组织结构, 部门主管等角色定义在manager表中 前端控制器
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-22
 */

@Tag(name = "运维账户", description = "400")
@RestController
@RequestMapping("/department")
public class DepartmentController extends BaseSpringSecurityController {

    @Autowired
    private DepartmentApplicationService departmentApplicationService;

    @Operation(summary = "创建部门", operationId = "201")
    @PostMapping(value = "")
    public HttpRetData add(@RequestBody DepartmentSaveCmd req) {
        departmentApplicationService.saveDepartment(req);
        return HttpRetData.success();
    }


    @Operation(summary = "编辑部门", method = "", operationId = "202")
    @PutMapping(value = "")
    public HttpRetData update(@RequestBody DepartmentUpdateCmd req) {
        departmentApplicationService.updateDepartment(req);
        return HttpRetData.success();
    }

    @Operation(summary = "查询机构", method = "/org", operationId = "203")
    @GetMapping(value = "/org")
    public HttpRetData getOrgs() {
        SessionUserContextVO ju = (SessionUserContextVO)getCurrentUserObject();
        if ( ju != null && ju.getOrgId() != null){
            DepartmentVO v = departmentApplicationService.getDepartment(ju.getOrgId());
            List<DepartmentVO> r = new ArrayList<>();
            r.add(v);
            return HttpRetData.success(r);
        }
        List<DepartmentVO> vo = departmentApplicationService.getOrgs();

        return HttpRetData.success(vo);
    }

    @Operation(summary = "查询部门详情", method = "/**", operationId = "204")
    @GetMapping(value = "/{id}")
    public HttpRetData get(@PathVariable Long id) {
        DepartmentVO vo = departmentApplicationService.getDepartment(id);
        return HttpRetData.success(vo);
    }

    @Operation(summary = "查询部门树", method = "/tree", operationId = "205")
    @PostMapping(value = "/tree")
    public HttpRetData tree(@RequestParam(required = false)Long fid) {
        SessionUserContextVO ju = (SessionUserContextVO)getCurrentUserObject();
        DepartmentQuery query = new DepartmentQuery(fid, null);
        if (ju.getPermissionCode() != null){
            query.setPermissionCode(ju.getPermissionCode());
        }
        RetPage<DepartmentVO> ret = departmentApplicationService.listDepartment(query);
        List<DepartmentVO> vos = Tree.assembleTree(ret.getRecords());
        return HttpRetData.success(vos);
    }

    @Operation(summary = "查询部门列表", method = "/list", operationId = "206")
    @PostMapping(value = "/list")
    public HttpRetData list(@RequestBody DepartmentQuery req) {
        SessionUserContextVO ju = (SessionUserContextVO)getCurrentUserObject();
        if (ju.getOrgId() != null){
            req.setOrgId(ju.getOrgId());
        }
        RetPage<DepartmentVO> ret = departmentApplicationService.listDepartment(req);
        return HttpRetData.success(ret);
    }


    @Operation(summary = "删除部门", method = "/*", operationId = "207")
    @DeleteMapping(value = "/{id}")
    public HttpRetData delete(@PathVariable Long id) {

        departmentApplicationService.deleteDepartment(id);
        return HttpRetData.success();
    }
}

