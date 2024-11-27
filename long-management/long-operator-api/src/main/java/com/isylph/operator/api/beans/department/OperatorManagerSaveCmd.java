package com.isylph.operator.api.beans.department;

import com.isylph.basis.base.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 部门主管配置
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OperatorManagerSaveCmd extends BaseCmd {

    private Long departmentId;

    private List<OperatorManagerVO> managers;


}
