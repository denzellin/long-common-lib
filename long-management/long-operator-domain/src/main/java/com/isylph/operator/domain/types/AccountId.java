package com.isylph.operator.domain.types;

import com.isylph.basis.base.BaseType;
import lombok.Getter;

@Getter
public class AccountId extends BaseType {

    private Long accountId;

    public AccountId(Long accountId) {
        this.accountId = accountId;
    }
}
