package com.isylph.basis.types;


import com.isylph.basis.base.BaseType;
import com.isylph.basis.error.TypeErrors;
import com.isylph.basis.controller.exception.ReturnException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 事物的启用、禁用状态
 * </p>
 *
 * @author denzel.lin
 * @since 2020-02-12
 */
@Slf4j
@Getter
@Builder(toBuilder=true)
public class Activate  extends BaseType {


    private Integer status;

    public Activate(Integer status) {
        if(null == status || (status != 0 && status != 1)){
            log.warn("invalid status value: {}", status);
            throw new ReturnException(TypeErrors.INVALID_ACTIVATE_STATUS);
        }
        this.status = status;
    }

    public static Activate active(){
        return new Activate(1);
    }

    public static Activate inactive(){
        return new Activate(0);
    }
}
