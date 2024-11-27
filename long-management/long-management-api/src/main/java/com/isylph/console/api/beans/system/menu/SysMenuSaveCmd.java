package com.isylph.console.api.beans.system.menu;

import com.isylph.basis.base.BaseCmd;
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
public class SysMenuSaveCmd extends BaseCmd {

    private Long fid;

    private String title;

    private String titleExt;

    private String name;

    private String path;

    private String icon;

    private String rgba;

    private String component;

    private Integer sort;

    private Integer active;

    private Integer prop;

    private Integer notCache;

    private Integer hideInMenu;

    private Integer hideInBread;

    private String beforeCloseName;

    private Integer vary;


}
