package com.isylph.operator.api.beans.service;

import com.isylph.basis.base.RetPage;
import com.isylph.operator.api.beans.operator.SysOperatorQuery;
import com.isylph.operator.api.beans.operator.SysOperatorVO;

public interface OperatorInterfaceService {

    SysOperatorVO getOperator(Long accountId);

    SysOperatorVO getOperator(String account);

    RetPage<SysOperatorVO> queryOperator(SysOperatorQuery req);
}
