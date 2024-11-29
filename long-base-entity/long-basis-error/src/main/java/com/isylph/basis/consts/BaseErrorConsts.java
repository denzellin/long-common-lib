package com.isylph.basis.consts;


import com.isylph.basis.entity.ErrorBase;

/**
 * 服务接口返回值常量
 *
 * @author Denzel Lin
 * @Date 2017-04-28
 */
public class BaseErrorConsts {

    public static final Long RET_OK = 0L; //响应成功
    public static final Long RET_ERROR = 1L; //响应失败
    public static final Long RET_RPC_ERR = 2L; //远程服务错误

    public static final Long LOGIN_TIMEOUT = 401L;//登录超时


    public static final String RET_OK_MSG = "操作成功"; //响应成功

    public static final String RET_ERROR_MSG = "服务器繁忙，请稍后再试"; //响应失败

    public static final Long RET_FORBIDDEN = 403L;

    public static final Long RET_NOT_FOUND = 404L;

    public static final Long RET_CONFLICT = 409L;

    public static final Long RET_BAD_PARAM = 422L;

    public static final Long RET_INTERNAL_ERR = 500L;


    /**
     * 全局领域类型定义
     */
    public static final long RET_TYPES_BASE         = 10000;
    public static final long RET_AUX_EXCEL          = 10100;
    public static final long OSS_BASE               = 10200;
    public static final long RET_SYSTEM_BASE        = 12000;
    public static final long RET_OPERATOR_BASE      = 12100;
    public static final long RET_DEPARTMENT_BASE    = 12200;



    /**
     * demo function
     * @return
     */
    public static long define(ErrorBase base){

        long code = base.getCode();
        base.setCode(code + 1);
        return code;
    }
}
