package com.isylph.operator.api.consts;


import com.isylph.basis.anno.ErrorItem;
import com.isylph.basis.anno.ErrorManager;
import com.isylph.basis.consts.RetCodeConsts;

@ErrorManager("系统管理")
public class RetCode {

    public static final long BASE = RetCodeConsts.RET_SYSTEM_BASE;

    @ErrorItem("登录超时")
    public static final long LOGIN_TIMEOUT = 500001;//登录超时

    @ErrorItem("用户不存在")
    public static final long ERR_NO_ACCOUNT = BASE + 1;//用户不存在

    @ErrorItem("密码错误")
    public static final long ERR_PASSWORD = BASE + 2;//密码错误

    @ErrorItem("当前账号不支持该操作")
    public static final long ERR_SYSTEM_ACCOUNT = BASE + 100;//当前账号不支持该操作

    @ErrorItem("无账号")
    public static final long NO_ACCOUNT = BASE + 3;//无账号

    @ErrorItem("原密码错误")
    public static final long ERR_SYS_INVALID_OLD_PASSWORD = BASE + 8;//原密码错误

    @ErrorItem("新旧密码一样")
    public static final long SAME_AS_OLD_AND_NEW_PASSWORDS = BASE + 13;//新旧密码一样

    @ErrorItem("没有找到错误描述信息")
    public static final long ERR_NO_ANNOTATION_FOUND = BASE + 9;

    @ErrorItem("没有消息描述")
    public static final long ERR_NO_EMPTY_MSG = BASE + 10;

    @ErrorItem("该用户名已存在")
    public static final long ERR_USER_NAME_ALREADY_EXISTS = BASE + 11;

    public static final long RET_BASE_SYSTEM_MENU = BASE +1000;

    @ErrorItem("群组不存在")
    public static final long GROUP_NOT_EXIST = BASE + 4006;


}
