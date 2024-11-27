package com.isylph.console.api.beans.system.func;

import com.isylph.basis.base.BaseListQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysFuncQuery extends BaseListQuery {

    private String name;


    private Long groupId;

}
