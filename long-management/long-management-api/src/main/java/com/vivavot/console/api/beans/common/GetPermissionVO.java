package com.vivavot.console.api.beans.common;

import com.vivavot.basis.beans.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetPermissionVO extends BaseVO {

    List<PermissionVO> urls;
}
