package com.isylph.basis.consts;

/**
 * 服务接口返回值常量
 *
 * @author Denzel Lin
 * @Date 2017-04-28
 */
public class RetCodeConsts {

    public static final Long RET_OK = 0L; //响应成功
    public static final Long RET_ERROR = 1L; //响应失败
    public static final Long RET_RPC_ERR = 2L; //远程服务错误


    public static final Long SERVICE_UNAVAILABLE = 3L; //远程服务错误
    public static final Long LOGIN_TIMEOUT = 401L;//登录超时


    public static final String RET_OK_MSG = "操作成功"; //响应成功
    public static final String RET_ERROR_MSG = "服务器繁忙，请稍后再试"; //响应失败

    public static final String RET_BAD_REQ_MSG = "您的输入不正确";

    public static final Long RET_FORBIDDEN = 403L;

    public static final Long RET_NOT_FOUND = 404L;
    public static final String RET_NOT_FOUND_MSG = "没有找到您请求的内容";

    public static final Long RET_CONFLICT = 409L;

    public static final Long RET_BAD_PARAM = 422L;

    public static final Long RET_INTERNAL_ERR = 500L;
    public static final String RET_INTERNAL_ERR_MSG = "服务器忙";


    /**
     * 全局领域类型定义
     */
    public static final long RET_TYPES_BASE     = 10000;
    public static final long RET_AUX_EXCEL      = 10100;
    public static final long RET_ACCOUNT_BASE   = 11000;
    public static final long RET_MESSAGE_BASE   = 12000;
    public static final long RET_SYSTEM_BASE    = 13000;
    public static final long RET_OPERATOR_BASE  = 14000;
    public static final long RET_DEPARTMENT_BASE  = 15000;
    public static final long OSS_BASE           = 18000;

    public static final long RET_MEMBER_BASE       = 20000;
    public static final long RET_ORG_BASE       = 25000;
    public static final long RET_PORTAL_BASE    = 26000;
    public static final long RET_SERVICE_BASE    = 28000;
}
