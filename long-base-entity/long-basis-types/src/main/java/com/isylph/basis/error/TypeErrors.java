package com.isylph.basis.error;


import com.isylph.basis.anno.ErrorItem;
import com.isylph.basis.anno.ErrorManager;
import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.entity.ErrorBase;

@ErrorManager("全局领域类型")
public class TypeErrors extends BaseErrorConsts {

    private static final ErrorBase BASE = new ErrorBase(BaseErrorConsts.RET_TYPES_BASE);


    @ErrorItem("手机号码长度错误")
    public final static long INVALID_LENGTH = define(BASE);

    @ErrorItem("状态无效")
    public final static long INVALID_ACTIVATE_STATUS  = define(BASE);

    @ErrorItem("身份证号无效")
    public final static long INVALID_ID_NO = define(BASE);


}
