package com.isylph.operator.application;


import com.isylph.basis.base.RetPage;
import com.isylph.operator.api.beans.department.OperatorManagerSaveCmd;
import com.isylph.operator.api.beans.department.OperatorManagerVO;
import com.isylph.operator.api.beans.operator.SysOperatorQuery;
import com.isylph.operator.api.beans.operator.SysOperatorSaveCmd;
import com.isylph.operator.api.beans.operator.SysOperatorUpdateCmd;
import com.isylph.operator.api.beans.operator.SysOperatorVO;
import com.isylph.operator.domain.types.Account;
import com.isylph.operator.domain.types.AccountId;

import java.util.List;

public interface OperatorApplicationService {

    SysOperatorVO login(String account, String password);

    Long createOperator(SysOperatorSaveCmd req);

    void updateOperator(SysOperatorUpdateCmd req);

    SysOperatorVO getOperator(Long accountId);

    SysOperatorVO getOperator(String account);

    RetPage<SysOperatorVO> queryOperator(SysOperatorQuery req);

    void removeOperator(Long accountId);

    void updatePassword(Long accountId, String newz);

    List<OperatorManagerVO> queryOperatorManger(Long deptId);

    void saveOperatorManger(OperatorManagerSaveCmd req);
}
