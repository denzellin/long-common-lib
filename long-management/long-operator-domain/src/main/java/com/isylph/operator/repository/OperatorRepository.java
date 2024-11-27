package com.isylph.operator.repository;


import com.isylph.basis.base.RetPage;
import com.isylph.operator.api.beans.operator.SysOperatorQuery;
import com.isylph.operator.domain.entity.OperatorManager;
import com.isylph.operator.domain.entity.SysOperator;
import com.isylph.operator.domain.types.Account;
import com.isylph.operator.domain.types.AccountId;

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
