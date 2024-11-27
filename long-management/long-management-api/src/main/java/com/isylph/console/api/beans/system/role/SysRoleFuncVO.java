package com.isylph.console.api.beans.system.role;


import com.isylph.basis.beans.BaseVO;
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
public class SysRoleFuncVO extends BaseVO {

    private Long id;

    private Long roleId;

    private Long funcId;


}
