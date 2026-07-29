package com.vivavot.oss.api.consts;


import com.vivavot.basis.anno.ErrorItem;
import com.vivavot.basis.anno.ErrorManager;
import com.vivavot.basis.consts.BaseErrorConsts;
import com.vivavot.basis.entity.ErrorBase;

@ErrorManager("文件服务")
public class Errors extends BaseErrorConsts{
    private static final ErrorBase BASE = new ErrorBase( BaseErrorConsts.OSS_BASE);

    @ErrorItem("文件不能为空")
    public static final long UPLOAD_FILE_MUST_NOT_EMPTY = define(BASE);

    @ErrorItem("文件后缀名无效")
    public static final long UPLOAD_INVALID_SUFFIX = define(BASE);

    @ErrorItem("文件格式无效")
    public static final long UPLOAD_INVALID_FORMAT = define(BASE);

    @ErrorItem("无法保存文件")
    public static final long UPLOAD_SAVE_FILE_FAIL = define(BASE);

    @ErrorItem("文件存储配置无效")
    public static final long LOCATION_CFG_NO_EXIST = define(BASE);

    @ErrorItem("文件不存在")
    public static final long FILE_NOT_EXIST = define(BASE);
}
