package com.isylph.console.api.beans.system.role;

import com.isylph.basis.base.BaseListQuery;
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
public class SysRoleMenuQuery extends BaseListQuery {

    private Long roleId;

}
