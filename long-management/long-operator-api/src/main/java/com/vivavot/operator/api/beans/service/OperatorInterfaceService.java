package com.vivavot.operator.api.beans.service;

import com.vivavot.basis.base.RetPage;
import com.vivavot.operator.api.beans.operator.SysOperatorQuery;
import com.vivavot.operator.api.beans.operator.SysOperatorVO;

public interface OperatorInterfaceService {

    SysOperatorVO getOperator(Long accountId);

    SysOperatorVO getOperator(String account);

    RetPage<SysOperatorVO> queryOperator(SysOperatorQuery req);
}
