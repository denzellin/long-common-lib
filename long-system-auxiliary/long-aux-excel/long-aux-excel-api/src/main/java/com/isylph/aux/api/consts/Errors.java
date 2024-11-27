package com.isylph.aux.api.consts;


import com.isylph.basis.anno.ErrorItem;
import com.isylph.basis.anno.ErrorManager;
import com.isylph.basis.consts.RetCodeConsts;

@ErrorManager("导入导出")
public class Errors {

    private static long BASE = RetCodeConsts.RET_AUX_EXCEL;
    private static long define(){
        BASE++;
        return BASE;
    }

    @ErrorItem("配置信息无法找到")
    public static final long CFG_INFO_NOT_FOUND = define();

    @ErrorItem("配置信息无效")
    public static final long CFG_INFO_INVALID = define();

    @ErrorItem("列配置信息无效")
    public static final long CFG_COLUMN_INFO_INVALID = define();

    @ErrorItem("模板文件打开失败")
    public static final long TEMPLATE_ACCESS_FAILURE = define();
}
