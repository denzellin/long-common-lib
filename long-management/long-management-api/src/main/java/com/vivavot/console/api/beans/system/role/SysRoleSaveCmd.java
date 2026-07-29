package com.vivavot.console.api.beans.system.role;

import com.vivavot.basis.base.BaseCmd;
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
public class SysRoleSaveCmd extends BaseCmd {

    private String name;

    private String role;

    private String url;

    private String remark;

}
