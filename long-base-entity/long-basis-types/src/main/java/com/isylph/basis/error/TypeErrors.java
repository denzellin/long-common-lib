package com.isylph.basis.error;


import com.isylph.basis.anno.ErrorItem;
import com.isylph.basis.anno.ErrorManager;
import com.isylph.basis.consts.RetCodeConsts;

@ErrorManager("全局领域类型")
public class TypeErrors {

    final static long BASE = RetCodeConsts.RET_TYPES_BASE;

    @ErrorItem("手机号码长度错误")
    public final static long INVALID_LENGTH = BASE + 1;

    @ErrorItem("微信ID无效")
    public final static long INVALID_WECHAT_ID = BASE + 2;

    @ErrorItem("性别错误")
    public final static long INVALID_GENDER = BASE + 3;

    @ErrorItem("支付平台无效")
    public final static long INVALID_PAYMENT_PLATFORM = BASE + 4;

    @ErrorItem("用户类别为空")
    public final static long INVALID_USER_TYPE = BASE + 5;

    @ErrorItem("状态无效")
    public final static long INVALID_ACTIVATE_STATUS = BASE + 6;

    @ErrorItem("价格无效")
    public final static long INVALID_NEGATIVE_PRICE = BASE + 7;

    @ErrorItem("商品的服务类别无效")
    public final static long INVALID_ORDER_PRODUCT_SERVICE_TYPE = BASE + 8;

    @ErrorItem("身份证号无效")
    public final static long INVALID_ID_NO = BASE + 9;


}
