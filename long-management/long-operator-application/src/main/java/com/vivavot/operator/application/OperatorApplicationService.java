package com.vivavot.operator.application;


import com.vivavot.basis.base.RetPage;
import com.vivavot.operator.api.beans.department.OperatorManagerSaveCmd;
import com.vivavot.operator.api.beans.department.OperatorManagerVO;
import com.vivavot.operator.api.beans.operator.SysOperatorQuery;
import com.vivavot.operator.api.beans.operator.SysOperatorSaveCmd;
import com.vivavot.operator.api.beans.operator.SysOperatorUpdateCmd;
import com.vivavot.operator.api.beans.operator.SysOperatorVO;

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
