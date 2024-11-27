package com.isylph.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.springframework.util.StringUtils {

    public static boolean isEmpty(String s){
        return !org.springframework.util.StringUtils.hasLength(s);
    }

    public static boolean isNotEmpty(String s){

        return !isEmpty(s);
    }


    public static String left(String str, int size) {
        if (str == null) {
            return null;
        }
        if (str.length() > size) {
            return str.substring(0, size);
        } else {
            return str;
        }
    }

    public static final String replace(String line, String oldString, String newString) {
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {

            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = line.indexOf(oldString, i)) > 0) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return line;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String getRandomNum(int length) {
        Random r = new Random();
        String rs = "";
        String rn = "";
        for (int i = 0; i < length; i++) {
            rn = String.valueOf(r.nextInt(10));
            rs += rn;
        }
        return rs;
    }

    public static Double parseNumeric(String str) {
        Double d = null;
        try {
            d = Double.parseDouble(str);
        } catch (Exception e) {
            return null;//异常 说明包含非数字。
        }
        return d;
    }
}
