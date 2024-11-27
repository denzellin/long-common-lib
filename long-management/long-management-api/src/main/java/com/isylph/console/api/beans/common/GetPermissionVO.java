package com.isylph.console.api.beans.common;

import com.isylph.basis.beans.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetPermissionVO extends BaseVO {

    List<PermissionVO> urls;
}
