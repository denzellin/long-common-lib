package com.isylph.oss.api.entity;

import com.isylph.basis.beans.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OssFileInfoVO extends BaseVO {

    private String url;

    private String guid;

    private String name;

    private String path;
}
