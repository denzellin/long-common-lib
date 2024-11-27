package com.isylph.console.api.beans.system.menu;

import com.isylph.basis.base.BaseCmd;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MenuActivateCmd extends BaseCmd {

    private Long id;

    private Integer active;

}
