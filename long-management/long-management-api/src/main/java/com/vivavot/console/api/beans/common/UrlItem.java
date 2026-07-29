package com.vivavot.console.api.beans.common;

import com.vivavot.basis.beans.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UrlItem extends BaseVO {

    private String url;

    private String method;
}
