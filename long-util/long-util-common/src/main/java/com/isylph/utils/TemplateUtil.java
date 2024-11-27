package com.isylph.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TemplateUtil {


	/*组装返回权限给前台*/
	public static List<String> getKeyWords(String templatee){
	    Pattern p = Pattern.compile("\\{(.*?)\\}");
        Matcher m = p.matcher(templatee);
        List<String> strs = new ArrayList<String>();
        while (m.find()) {
            strs.add(m.group(1));
        }
        return strs;
	}

	public static String replaceKeywords(String str, String key, String val) {
	    String patternString = "\\{" + key +"\\}";

	    return str.replaceAll(patternString, val);
	}

}
