package com.isylph.operator;


import com.isylph.basis.base.RetPage;
import com.isylph.basis.beans.HttpRetData;
import com.isylph.operator.api.beans.operator.PasswordModifyCmd;
import com.isylph.operator.api.beans.operator.SysOperatorQuery;
import com.isylph.operator.api.beans.operator.SysOperatorSaveCmd;
import com.isylph.operator.api.beans.operator.SysOperatorUpdateCmd;
import com.isylph.operator.api.beans.operator.SysOperatorVO;
import com.isylph.operator.application.OperatorApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-19
 */
@Slf4j
@Tag(name = "运维账户", description = "400")
@RestController
@RequestMapping("/console/operator")
public class SysOperatorController{

    @Autowired
    private OperatorApplicationService operatorApplicationService;

    @Operation(summary = "用户账号", method = "/**", operationId = "001")
    public HttpRetData demo() {
        return HttpRetData.success();
    }

    @PostMapping("/c")
    @Operation(summary = "创建用户")
    HttpRetData add( @RequestBody SysOperatorSaveCmd request) {
        operatorApplicationService.createOperator(request);
        return HttpRetData.success();
    }

    @PostMapping(path = "/d")
    @Operation(summary = "删除数据")
    HttpRetData deleteByPrimaryKey(@RequestParam Long id) {
        operatorApplicationService.removeOperator(id);
        return HttpRetData.success();
    }


    @PostMapping(path = {"/u"})
    @Operation(summary = "编辑记录")
    HttpRetData updateByPrimaryKey(@RequestBody SysOperatorUpdateCmd request) {
        operatorApplicationService.updateOperator(request);
        return HttpRetData.success();
    }

    @GetMapping(path = {"/r/{id}"})
    @Operation(summary = "根据主键查询")
    HttpRetData getByPrimaryKey(@PathVariable(name = "id") Long id) {

        SysOperatorVO vo = operatorApplicationService.getOperator(id);
        return HttpRetData.success(vo);

    }

    @PostMapping(path = "/r")
    @Operation(summary = "根据筛选条件查询列表")
    HttpRetData listByFilter(@RequestBody SysOperatorQuery query) {

        RetPage<SysOperatorVO> ret = operatorApplicationService.queryOperator(query);
        return HttpRetData.success(ret.getRecords(), ret.getTotal());
    }


    @Operation(summary = "修改密码")
    @PostMapping(value = "/password")
    public HttpRetData changePassword(@RequestBody PasswordModifyCmd vo){

        operatorApplicationService.updatePassword(vo.getOperatorId(), vo.getPassword());
        return HttpRetData.success();
    }
}

