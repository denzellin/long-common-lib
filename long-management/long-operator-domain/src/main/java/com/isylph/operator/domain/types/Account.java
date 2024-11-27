package com.isylph.operator.domain.types;

import com.isylph.basis.base.BaseType;
import lombok.Getter;


@Getter
public class Account extends BaseType {

    private String account;

    public Account(String account) {
        this.account = account;
    }
}
