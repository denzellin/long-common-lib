package com.isylph.utils.encryption;

import java.util.UUID;
import java.util.regex.Pattern;

public class PasswordUtils {

    public static final String REGEX_PASSWORD_STRONG = "(?=^.{8,}$)(?=.*\\d)(?=.*[`!@#$%^&*()\\\\|\\{\\}\\[\\]:;\",'.?/]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";


    public static boolean isStrongPassword(String pwd){
        if (!Pattern.matches(REGEX_PASSWORD_STRONG, pwd)){
            return false;
        }
        return true;
    }

    public static String getSalt() {
        return UUID.randomUUID().toString().replace("-", "");
    };

    public static String encryptPassword(String origin, String salt) {
        return MD5Utils.getMD5(origin +salt);
    }
}
