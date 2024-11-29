package com.isylph.oss.controller;


import com.isylph.basis.beans.HttpRetData;
import com.isylph.oss.api.entity.OssFileLocationQuery;
import com.isylph.oss.api.entity.OssFileLocationSaveCmd;
import com.isylph.oss.api.entity.OssFileLocationUpdateCmd;
import com.isylph.oss.application.OssApplicationService;
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
 * 系统中静态资源文件的保存路径 前端控制器
 * </p>
 *
 * @author denzel.lin
 * @since 2019-01-08
 */
@Slf4j
@RestController
@Tag(name = "文件模块管理", description = "")
@RequestMapping("oss/module")
public class AuxFileLocationController{

    @Autowired
    private OssApplicationService ossApplicationService;


    @Operation(summary = "获取模块路径")
    @GetMapping(value = "/module/{module}")
    public HttpRetData getModulePath(@PathVariable String module) {

        return HttpRetData.success(ossApplicationService.findOssFileLocation(module));
    }


    @PostMapping("/c")
    @Operation(summary = "新增")
    HttpRetData add(@RequestBody OssFileLocationSaveCmd request) {
        ossApplicationService.saveOssFileLocation(request);
        return HttpRetData.success();
    }


    @PostMapping(path = "/d")
    @Operation(summary = "删除数据")
    HttpRetData deleteByPrimaryKey(@RequestParam Long id) {
        ossApplicationService.removeOssFileLocation(id);
        return HttpRetData.success();
    }

    @PostMapping(path = {"/u"})
    @Operation(summary = "编辑记录")
    HttpRetData updateByPrimaryKey(@RequestBody OssFileLocationUpdateCmd request) {
        ossApplicationService.updateOssFileLocation(request);
        return HttpRetData.success();
    }



    @GetMapping(path = {"/r/{id}"})
    @Operation(summary = "根据主键查询")
    HttpRetData getByPrimaryKey(@PathVariable(name = "id") Long id) {


        return HttpRetData.success(ossApplicationService.findOssFileLocation(id));

    }

    @PostMapping(path = "/r")
    @Operation(summary = "根据筛选条件查询列表")
    HttpRetData listByFilter(@RequestBody OssFileLocationQuery query) {
        return HttpRetData.success(ossApplicationService.queryOssFileLocation(query));
    }


}

