package com.isylph.console.api.beans.system.func;

import com.isylph.basis.base.BaseCmd;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class SysFuncGroupSaveCmd extends BaseCmd {

    @Schema(name ="分组Id")
    private Long id;

    @Schema(name = "分组名称")
    private String name;


}
