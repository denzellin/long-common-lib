package com.isylph.basis.controller.exception;

import com.isylph.basis.beans.HttpRetData;
import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@Slf4j
public abstract class RestExceptionBaseHandler {


    abstract protected String getMessage(Long code);


    @ExceptionHandler(IllegalAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    protected  <T> HttpRetData<T> BadCredentialsExceptionHandler(Exception e, HttpServletRequest request) {
        return HttpRetData.error(401L, e.getMessage());
    }
    /**
     * 系统检查错误
     */
    @ExceptionHandler(ReturnException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    protected  <T> HttpRetData<T> ReturnExceptionExceptionHandler(Exception e, HttpServletRequest request) {
        HttpRetData<T> resultData = new HttpRetData<>();
        if (e instanceof ReturnException) {

            Long code = ((ReturnException) e).getCode();
            String errorMessage = ((ReturnException) e).getErrorMessage();

            if (!StringUtils.isEmpty(errorMessage)) {
                if (code == null) {
                    resultData.set(BaseErrorConsts.RET_BAD_PARAM, errorMessage, null, null);
                } else {
                    resultData.set(code, errorMessage, null, null);
                }
            } else if (code != null) {
                 resultData.set(code, getMessage(code), null, null);
            } else {
                resultData.set(BaseErrorConsts.RET_ERROR, BaseErrorConsts.RET_ERROR_MSG, null, null);
            }
            log.error("error message:{}",resultData);
        }else if (e instanceof ConstraintViolationException){
            ConstraintViolationException exception = (ConstraintViolationException)e;
            String msg = null;
            for (ConstraintViolation value : ((ConstraintViolationException) e).getConstraintViolations()) {
                msg = value.getMessageTemplate() + " ";
            }
            resultData.set(BaseErrorConsts.RET_BAD_PARAM, msg, null, null);
        } else if ( e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException)e;
            resultData.set(BaseErrorConsts.RET_BAD_PARAM, exception.getBindingResult().getFieldError().getDefaultMessage(), null, null);
        } else {
            log.error("request url:{}",request.getRequestURI());
            log.error("--------->Application exception occurred: {}!", e);
            resultData.set(BaseErrorConsts.RET_ERROR, BaseErrorConsts.RET_ERROR_MSG, null, null);
        }
        return resultData;
    }
}
