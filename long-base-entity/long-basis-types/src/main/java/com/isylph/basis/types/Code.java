package com.isylph.basis.types;

import com.isylph.basis.base.BaseType;
import com.isylph.utils.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 分级编码
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
public class Code  extends BaseType {

    private String code;

    public Code(String... codes){

        if (codes.length == 0){
            code = null;
            return;
        }
        code = codes[0];
        for(int i = 1; i < codes.length; i++){
            code += '.' + codes[i];
        }
        return;
    }


    List<String> toArray(){

        if (StringUtils.isEmpty(code)){
            return null;
        }

        List<String> codes = Arrays.asList(StringUtils.split(code, "\\."));

        return codes;
    }

    List<Long> toLongArray(){

        List<String> ss = toArray();
        if (null == ss){
            return null;
        }

        List<Long> codes = new ArrayList<>();
        for (int i = 0; i < ss.size(); i++){
            codes.add(Long.valueOf(ss.get(i)));
        }

        return codes;
    }
}
