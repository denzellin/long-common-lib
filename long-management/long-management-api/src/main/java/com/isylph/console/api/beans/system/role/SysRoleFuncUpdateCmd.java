package com.isylph.console.api.beans.system.role;

import com.isylph.basis.base.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleFuncUpdateCmd extends BaseCmd {

    private Long id;

    private Long roleId;

    private Long funcId;


}
