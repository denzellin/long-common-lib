package com.isylph.console.api.beans.system.func;

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
public class SysFuncSaveCmd extends BaseVO {

    private String name;

    private Integer active;

    private Long groupId;

    private String url;

}
