package com.isylph.error.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isylph.basis.beans.HttpRetData;
import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.controller.CrudController;
import com.isylph.error.api.beans.AuxErrorMessageDTO;
import com.isylph.error.api.beans.ErrorDTO;
import com.isylph.error.api.beans.AuxErrorMessageQuery;
import com.isylph.error.api.beans.AuxErrorMessageSaveCmd;
import com.isylph.error.api.beans.AuxErrorMessageUpdateCmd;
import com.isylph.error.conf.ErrorAnnotationScanner;
import com.isylph.error.model.AuxErrorMessagePO;
import com.isylph.error.service.AuxErrorMessageService;
import com.isylph.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-19
 */
@Slf4j
@Tag(name = "系统管理", description = "100")
@RestController
@RequestMapping("/errors")
public class SysErrorMessageController
        implements CrudController<AuxErrorMessageSaveCmd, AuxErrorMessageUpdateCmd,
        AuxErrorMessageQuery, AuxErrorMessageDTO, AuxErrorMessagePO> {

    @Autowired
    private AuxErrorMessageService sysErrorMessageService;

    @Override
    public BaseService<AuxErrorMessagePO> getService() {
        return sysErrorMessageService;
    }

    @Override
    public AuxErrorMessagePO createModel() {
        return new AuxErrorMessagePO();
    }

    @Override
    public AuxErrorMessageDTO createViewObject() {
        return new AuxErrorMessageDTO();
    }

    @Operation(summary = "导入错误码")
    @PostMapping(value = "/load")
    public HttpRetData loadErrorAnnotation(@RequestParam(required = false) Long set){

        Map<Long, ErrorDTO> map = ErrorAnnotationScanner.getErrMap();
        if (null == map){
            log.error("no error annotation found.");
            throw new ReturnException(BaseErrorConsts.RET_NOT_FOUND);
        }

        for (Map.Entry<Long, ErrorDTO> entry : map.entrySet()) {
            ErrorDTO item = entry.getValue();

            AuxErrorMessagePO po = sysErrorMessageService.getById(item.getVal());
            if (null == po){
                po = new AuxErrorMessagePO()
                        .setId(item.getVal())
                        .setModule(item.getModule())
                .setMessage(item.getAnnotation());
                sysErrorMessageService.saveError(po);
            } else {
                if (set != null && set == 1){
                    po.setMessage(item.getAnnotation())
                            .setModule(item.getModule());
                    sysErrorMessageService.updateById(po);
                }

            }
        }

        return HttpRetData.success();
    }

    @Override
    public HttpRetData<List<AuxErrorMessageDTO>> list(AuxErrorMessageQuery query) {
        Map<Long, ErrorDTO> map = ErrorAnnotationScanner.getErrMap();
        if (null == map){
            log.error("no error annotation found.");
        }

        Page<AuxErrorMessagePO> ret = sysErrorMessageService.selectViewListPage(query);
        List<AuxErrorMessagePO> list = ret.getRecords();

        List<AuxErrorMessageDTO> vl = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list) && !CollectionUtils.isEmpty(map)){
            for (AuxErrorMessagePO item : list){
                ErrorDTO err = map.get(item.getId());
                AuxErrorMessageDTO vo = new AuxErrorMessageDTO()
                        .setMessage(item.getMessage())
                        .setModule(item.getModule())
                        .setId(item.getId());

                if (null != err){
                    vo.setAnnotation(err.getAnnotation()).setName(err.getName());
                }
                vl.add(vo);
            }
        }
        return HttpRetData.success(vl, (int)ret.getTotal());
    }

    @Override
    public HttpRetData add(AuxErrorMessageSaveCmd request) {
        List<ErrorDTO> errs = request.getErrs();
        if (CollectionUtils.isEmpty(errs)){
            log.warn("save empty errors");
            throw new ReturnException(BaseErrorConsts.RET_NOT_FOUND);
        }
        for(ErrorDTO item: errs){
            AuxErrorMessagePO po = sysErrorMessageService.getById(item.getVal());
            if (null == po){
                po = new AuxErrorMessagePO()
                        .setId(item.getVal())
                        .setModule(item.getModule())
                        .setMessage(item.getAnnotation());

                sysErrorMessageService.save(po);

            } else {
                po.setMessage(item.getMessage());
                sysErrorMessageService.updateById(po);
            }
        }
        return HttpRetData.success();
    }
}

