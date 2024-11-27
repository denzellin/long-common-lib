package com.isylph.utils.validator;

public class AccountFormatCheckUtils {

    public static boolean checkFristCharIsLetter(String pStr){
        String reg = "^[a-zA-Z_]{1}[a-zA-Z0-9_]{3,19}$";
        boolean isbool = pStr.matches(reg);
        return isbool;
    }

    /*
     * 校验账号，条件：
     * 1. 由字母数字下划线组成且开头必须是字母
     * 2. 长度在4~20个字符之间
     * */
    public static boolean accountValidator(String account){
        String reg = "^[a-zA-Z_]{1}[a-zA-Z0-9_]{3,19}$";
        return account.matches(reg);
    }
}
