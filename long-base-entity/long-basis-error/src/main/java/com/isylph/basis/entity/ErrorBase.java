package com.isylph.basis.entity;

/**
 * <p>
 *
 * </p>
 *
 * @Author SYLPH Technologies Co., Ltd
 * @Date 2024/11/29 11:06
 * @Version 1.0
 */

public class ErrorBase {
    private Long code;

    public Long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public ErrorBase(long code) {
        this.code = code;
    }

    private ErrorBase() {
    }
}
