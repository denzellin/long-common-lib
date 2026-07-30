package com.vivavot;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

/**
 * <p>
 *
 * </p>
 *
 * @Author Denzel Lin
 * @Date 2024/12/19 16:22
 * @Version 1.0
 */
public class MysqlLongConvert extends MySqlTypeConvert {

    @Override
    public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
        if (fieldType != null && fieldType.toLowerCase().contains("tinyint(1)")) {
            return DbColumnType.INTEGER;
        }
        if (fieldType.contains("datetime")){
            return DbColumnType.LOCAL_DATE_TIME;
        }
        return super.processTypeConvert(config, fieldType);
    }
}
