package com.isylph.utils.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EmailFormatCheckUtils {

    private static String regExp="\\w+@\\w+(\\.\\w+)+";

    public static boolean isEmail(String str)throws PatternSyntaxException {

        if (null == str) {
            return false;
        }

        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /*必须包含@符号；必须包含点；点和@之间必须有字符
     * */
    public static boolean emailValidator(String email){
        String reg = "^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$";
        return email.matches(reg);
    }
}
