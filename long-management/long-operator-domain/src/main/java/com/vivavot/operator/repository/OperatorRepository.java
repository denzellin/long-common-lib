package com.vivavot.operator.repository;


import com.vivavot.basis.base.RetPage;
import com.vivavot.operator.api.beans.operator.SysOperatorQuery;
import com.vivavot.operator.domain.entity.OperatorManager;
import com.vivavot.operator.domain.entity.SysOperator;
import com.vivavot.operator.domain.types.Account;
import com.vivavot.operator.domain.types.AccountId;

import java.util.List;

public interface OperatorRepository {

    Long saveOperator(SysOperator req);

    RetPage<SysOperator> queryOperator(SysOperatorQuery req);

    SysOperator findOperator(Account account);

    SysOperator findOperator(AccountId accountId);

    void removeOperator(AccountId id);

    List<OperatorManager> getOperatorManagers(Long deptId);

    void saveOperatorManagers(List<OperatorManager> req);

    void removeOperatorManagers(Long deptId);
}
