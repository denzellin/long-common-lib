package com.isylph.basis.types;

/**
 * 手机号码
 * 长度：11
 *
 */


import com.isylph.basis.base.BaseType;
import com.isylph.basis.error.TypeErrors;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.utils.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
@Getter
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder=true)
@NoArgsConstructor
public class Mobile extends BaseType {

    private String mobile;


    public Mobile(String mobile) {

        if (StringUtils.isEmpty(mobile)){
            throw new ReturnException(TypeErrors.INVALID_LENGTH);
        }
        this.mobile = mobile;
    }

    public String desensitization(){
        if(StringUtils.isEmpty(mobile) || mobile.length() < 11){
            return mobile;
        }

        return mobile.substring(0, 3) + "****" + mobile.substring(7);
    }
    public static boolean validate(String e){

        if (StringUtils.isEmpty(e)){
            return false;
        }

        Pattern p = Pattern.compile("^(1[3-9])\\d{9}$");
        Matcher m = p.matcher(e);
        return  m.matches();
    }
}
