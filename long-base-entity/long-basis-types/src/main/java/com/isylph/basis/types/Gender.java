package com.isylph.basis.types;

import com.isylph.basis.base.BaseType;
import com.isylph.basis.error.TypeErrors;
import com.isylph.basis.controller.exception.ReturnException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * <p>
 * 性别
 * 性别 0:保密;1:男;2:女
 * </p>
 *
 * @author denzel.lin
 * @since 2020-02-12
 */

@ToString
@Getter
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder=true)
public class Gender extends BaseType {

    private final String male = "male";
    private final String female = "female";

    private String gender;

    public static Gender male(){
        return new Gender("male");
    }

    public static Gender female(){
        return new Gender("female");
    }

    public Gender(String gender) {
        if (null == gender){
            this.gender = "";
            return;
        }
        if (!male.equals(gender) && !female.equals(gender) ){
            throw new ReturnException(TypeErrors.INVALID_GENDER);
        }

        this.gender = gender;
    }
}
