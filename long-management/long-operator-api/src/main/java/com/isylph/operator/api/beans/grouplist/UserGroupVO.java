package com.isylph.operator.api.beans.grouplist;

import com.isylph.basis.beans.BaseVO;
import lombok.Data;

import java.util.Date;

@Data
public class UserGroupVO extends BaseVO {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String name;

    private Long roleId;

    private String roleName;

    private String remark;

}
