package com.isylph.basis.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class BaseCmd implements Serializable {

    /**
     * 发起请求的当前用户的ID
     */
    private Long opAccountId;

    /**
     * 发起请求的当前用户的姓名
     */
    private String opName;

    /**
     * 发起请求的当前用户的微信ID
     */
    private String opWechatId;

    /**
     * 发起请求的当前用户的类型
     */
    private String opType;

    /**
     * 发起请求的当前用户的账号
     */
    private String opAccount;

    /**
     * 发起请求的当前用户的工号
     */
    private String opEmployeeId;

    /**
     * 发起请求的当前用户的手机号码
     */
    private String opMobile;

}
