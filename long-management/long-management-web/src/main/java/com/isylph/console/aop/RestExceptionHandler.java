package com.isylph.console.aop;

import com.isylph.basis.consts.RetCodeConsts;
import com.isylph.basis.controller.exception.RestExceptionBaseHandler;
import com.isylph.error.model.AuxErrorMessagePO;
import com.isylph.error.service.AuxErrorMessageService;
import com.isylph.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends RestExceptionBaseHandler {

    @Autowired
    private AuxErrorMessageService sysErrorMessageService;

    @Override
    protected String getMessage(Long code){

        log.debug("error code: {}", code);
        if (null == code){
            return RetCodeConsts.RET_ERROR_MSG;
        }

        AuxErrorMessagePO err = sysErrorMessageService.getById(code);
        if (null != err && !StringUtils.isEmpty(err.getMessage())){
            return err.getMessage();
        }
        log.info("can't get err for code: {}", code);
        return code.toString();
    }
}
