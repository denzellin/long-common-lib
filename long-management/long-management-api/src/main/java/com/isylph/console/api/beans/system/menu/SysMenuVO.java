package com.isylph.console.api.beans.system.menu;

import com.isylph.basis.beans.Tree;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

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
@ToString(callSuper = true)
public class SysMenuVO extends Tree {

    private String title;

    private String name;

    private String path;

    private String icon;

    private String component;

    private Integer active;

    private Integer cache;

    private Integer hideInMenu;

    private Integer hideInBread;

    private Integer sys;

    private Integer user;

}
