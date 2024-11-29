package com.isylph.operator.api.consts;


import com.isylph.basis.anno.ErrorItem;
import com.isylph.basis.anno.ErrorManager;
import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.entity.ErrorBase;

@ErrorManager("系统管理")
public class Errors extends BaseErrorConsts{

    public static final ErrorBase BASE = new ErrorBase(BaseErrorConsts.RET_OPERATOR_BASE);


    @ErrorItem("登录超时")
    public static final long LOGIN_TIMEOUT = define(BASE);//登录超时

    @ErrorItem("用户不存在")
    public static final long ERR_NO_ACCOUNT =define(BASE);//用户不存在

    @ErrorItem("密码错误")
    public static final long ERR_PASSWORD = define(BASE);//密码错误

    @ErrorItem("当前账号不支持该操作")
    public static final long ERR_SYSTEM_ACCOUNT = define(BASE);//当前账号不支持该操作

    @ErrorItem("无账号")
    public static final long NO_ACCOUNT = define(BASE);//无账号

    @ErrorItem("原密码错误")
    public static final long ERR_SYS_INVALID_OLD_PASSWORD = define(BASE);//原密码错误

    @ErrorItem("新旧密码一样")
    public static final long SAME_AS_OLD_AND_NEW_PASSWORDS = define(BASE);//新旧密码一样

    @ErrorItem("没有找到错误描述信息")
    public static final long ERR_NO_ANNOTATION_FOUND = define(BASE);

    @ErrorItem("该用户名已存在")
    public static final long ERR_USER_NAME_ALREADY_EXISTS = define(BASE);

    @ErrorItem("群组不存在")
    public static final long GROUP_NOT_EXIST = define(BASE);


}
