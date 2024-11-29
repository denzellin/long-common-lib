package com.isylph.console.api.consts;


import com.isylph.basis.anno.ErrorItem;
import com.isylph.basis.anno.ErrorManager;
import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.entity.ErrorBase;

@ErrorManager("系统管理")
public class Errors extends BaseErrorConsts {

    public static final ErrorBase BASE = new ErrorBase(BaseErrorConsts.RET_SYSTEM_BASE);

    @ErrorItem("不能对默认权限角色添加成员")
    public static final long ERR_SYS_MEMBER_ROLE = define(BASE);//不能对默认权限角色添加成员

    @ErrorItem("权限角色名重复")
    public static final long ROLE_DULPLICATE_NAME = define(BASE);//权限角色名重复

    @ErrorItem("不能对系统权限角色进行修改")
    public static final long ROLE_EDIT_SYS_ROLE = define(BASE);//不能对系统权限角色进行修改

    @ErrorItem("权限角色不存在")
    public static final long ROLE_ROLE_NOT_EXIST = define(BASE);//权限角色不存在

    @ErrorItem("原密码错误")
    public static final long ERR_SYS_INVALID_OLD_PASSWORD = define(BASE);//原密码错误

    @ErrorItem("新旧密码一样")
    public static final long SAME_AS_OLD_AND_NEW_PASSWORDS = define(BASE);//新旧密码一样

    @ErrorItem("没有找到错误描述信息")
    public static final long ERR_NO_ANNOTATION_FOUND = define(BASE);

    @ErrorItem("没有消息描述")
    public static final long ERR_NO_EMPTY_MSG = define(BASE);

    @ErrorItem("该用户名已存在")
    public static final long ERR_USER_NAME_ALREADY_EXISTS = define(BASE);

    public static final long RET_BASE_SYSTEM_MENU = define(BASE);




}
