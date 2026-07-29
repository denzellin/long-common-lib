package com.vivavot.basis.types;

/**
 * 手机号码
 * 长度：11
 *
 */

import com.vivavot.basis.base.BaseType;
import com.vivavot.utils.StringUtils;
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
public class Email extends BaseType {

    private String email;


    public Email(String email) {

        this.email = email;
    }

    public static boolean validate(String e){

        if (StringUtils.isEmpty(e)){
            return false;
        }

        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(e);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }
}
