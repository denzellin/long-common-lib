package com.isylph.console.api.consts;


import com.isylph.basis.anno.ErrorItem;
import com.isylph.basis.anno.ErrorManager;
import com.isylph.basis.consts.RetCodeConsts;

@ErrorManager("系统管理")
public class RetCode {

    public static final long BASE = RetCodeConsts.RET_SYSTEM_BASE;

    @ErrorItem("不能对默认权限角色添加成员")
    public static final long ERR_SYS_MEMBER_ROLE = BASE + 4;//不能对默认权限角色添加成员

    @ErrorItem("权限角色名重复")
    public static final long ROLE_DULPLICATE_NAME = BASE + 5;//权限角色名重复

    @ErrorItem("不能对系统权限角色进行修改")
    public static final long ROLE_EDIT_SYS_ROLE = BASE + 6;//不能对系统权限角色进行修改

    @ErrorItem("权限角色不存在")
    public static final long ROLE_ROLE_NOT_EXIST = BASE + 7;//权限角色不存在

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

    @ErrorItem("找不到父级菜单")
    public static final long MENU_NO_FATHER = RET_BASE_SYSTEM_MENU + 1;//找不到父级菜单

    @ErrorItem("菜单不存在")
    public static final long MENU_NOT_EXIST = RET_BASE_SYSTEM_MENU + 3;//菜单不存在

    public static final long MENU_SUBMENU_EXIST = RET_BASE_SYSTEM_MENU + 4;//有子菜单存在



}
