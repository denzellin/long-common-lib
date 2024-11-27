package com.isylph.operator.api.beans.operator;

import com.isylph.basis.base.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordModifyCmd extends BaseCmd {

    private Long operatorId;

    private String password;
}
