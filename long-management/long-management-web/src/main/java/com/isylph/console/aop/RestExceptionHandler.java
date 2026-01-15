package com.isylph.console.aop;

import com.isylph.basis.beans.HttpRetData;
import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.controller.exception.RestExceptionBaseHandler;
import com.isylph.error.model.AuxErrorMessagePO;
import com.isylph.error.service.AuxErrorMessageService;
import com.isylph.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends RestExceptionBaseHandler {

    @Autowired
    private AuxErrorMessageService sysErrorMessageService;


    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    protected  <T> HttpRetData<T> authenticationExceptionHandler(Exception e, HttpServletRequest request) {
        log.warn("Exception: {}", e.getMessage());
        return HttpRetData.error(403L, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    protected  <T> HttpRetData<T> generalExceptionHandler(Exception e, HttpServletRequest request) {
        log.warn("Exception: {}", e.getMessage());
        return HttpRetData.error(422L, e.getMessage());
    }

    @Override
    protected String getMessage(Long code){

        log.debug("error code: {}", code);
        if (null == code){
            return BaseErrorConsts.RET_ERROR_MSG;
        }

        AuxErrorMessagePO err = sysErrorMessageService.getById(code);
        if (null != err && !StringUtils.isEmpty(err.getMessage())){
            return err.getMessage();
        }
        log.info("can't get err for code: {}", code);
        return code.toString();
    }
}
