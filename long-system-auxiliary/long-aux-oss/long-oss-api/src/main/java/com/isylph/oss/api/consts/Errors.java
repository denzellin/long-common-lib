package com.isylph.oss.api.consts;


import com.isylph.basis.anno.ErrorItem;
import com.isylph.basis.anno.ErrorManager;
import com.isylph.basis.consts.RetCodeConsts;

@ErrorManager("文件服务")
public class Errors {
    private static long BASE = RetCodeConsts.OSS_BASE;
    private static long define(){
        BASE++;
        return BASE;
    }

    @ErrorItem("文件不能为空")
    public static final long UPLOAD_FILE_MUST_NOT_EMPTY = define();

    @ErrorItem("文件后缀名无效")
    public static final long UPLOAD_INVALID_SUFFIX = define();

    @ErrorItem("文件格式无效")
    public static final long UPLOAD_INVALID_FORMAT = define();

    @ErrorItem("无法保存文件")
    public static final long UPLOAD_SAVE_FILE_FAIL = define();

    @ErrorItem("文件存储配置无效")
    public static final long LOCATION_CFG_NO_EXIST = define();
}
