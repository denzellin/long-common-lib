package com.vivavot.operator.api.beans.operator;

import com.vivavot.basis.base.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordModifyCmd extends BaseCmd {

    private Long operatorId;

    private String password;
}
