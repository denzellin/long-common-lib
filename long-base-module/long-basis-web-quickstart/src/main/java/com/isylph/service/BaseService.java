package com.isylph.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isylph.basis.base.BaseListQuery;
import com.isylph.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @param <T> 数据实体
 */
public interface BaseService<T> extends IService<T> {

    default String packCode(String... codes){

        if (codes.length == 0){
            return null;
        }
        String code = codes[0];
        for(int i = 1; i < codes.length; i++){
            code += '_' + codes[i];
        }
        return code;
    }

    default String packCode(Long... codes){

        if (codes.length == 0){
            return null;
        }

        String code = codes[0].toString();
        for(int i = 1; i < codes.length; i++){
            code += "_" + codes[i];
        }
        return code;
    }

    default List<String> unPackCode(String code){

        if (StringUtils.isEmpty(code)){
            return null;
        }

        List<String> codes = Arrays.asList(StringUtils.split(code, "_"));
        return codes;
    }

    default List<Long> unPackCodeLongValue(String code){

        List<String> ss = unPackCode(code);
        if (null == ss){
            return null;
        }

        List<Long> codes = new ArrayList<>();
        for (int i = 0; i < ss.size(); i++){
            codes.add(Long.valueOf(ss.get(i)));
        }

        return codes;
    }

    /**
     * 根据条件分页查询视图对象
     *
     * @param query 筛选条件
     * @return 视图对象列表
     */
    Page<T> selectViewListPage(BaseListQuery query);

    /**
     * 根据条件统计数量
     * @param query 筛选条件
     * @return
     */
    Integer countSelectViewListPage(BaseListQuery query);




}
