package com.isylph.basis.controller.exception;

public class ReturnException extends RuntimeException {

    private Long code;

    private String errorMessage;

    private String desc;

    public ReturnException() {
        super();
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ReturnException(Long code) {
        this.code = code;
    }

    public ReturnException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ReturnException(Long code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public ReturnException(String errorMessage, String desc) {
        this.errorMessage = errorMessage;
        this.desc = desc;
    }
}
