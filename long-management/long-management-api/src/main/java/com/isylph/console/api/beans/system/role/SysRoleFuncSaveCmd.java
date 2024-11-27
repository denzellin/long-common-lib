package com.isylph.console.api.beans.system.role;

import com.isylph.basis.base.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
public class SysRoleFuncSaveCmd extends BaseCmd {

    private Long roleId;

    private List<Long> funcIds;


}
