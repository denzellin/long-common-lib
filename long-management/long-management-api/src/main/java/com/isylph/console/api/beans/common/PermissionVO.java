package com.isylph.console.api.beans.common;

import com.isylph.basis.beans.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PermissionVO extends BaseVO {

    private String url;

    private String method;

    private Boolean permission;
}
