package com.isylph.aux.api.beans;

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
public class FileExportInfoDTO extends BaseVO {

    private String url;

    private String name;

    private String path;
}
