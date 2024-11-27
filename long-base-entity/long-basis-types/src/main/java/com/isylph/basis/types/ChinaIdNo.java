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
public class ChinaIdNo extends BaseType {
    private static final String regEx1 = "^[1-9][0-9]{5}(18|19|20)[0-9]{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)[0-9]{3}([0-9]|(X|x))";

    private String idNo;


    public ChinaIdNo(String idNo) {
        if(!validate(idNo)){
            throw new ReturnException(TypeErrors.INVALID_ID_NO);
        }

        this.idNo = idNo;
    }

    public static boolean validate(String e){

        if (StringUtils.isEmpty(e)){
            return false;
        }

        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(e);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }

    public static String desensitization(String idNo){
        if (StringUtils.isEmpty(idNo)){
            return idNo;
        }

        if (idNo.length() < 18 ){
            if(idNo.length() > 6){
                return idNo.substring(0, 6);
            }else {
                return idNo;
            }

        }

        return idNo.substring(0, 6) + "********" + idNo.substring(14);
    }
}
