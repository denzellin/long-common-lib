package com.isylph.error.api.beans;


import com.isylph.basis.base.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AuxErrorMessageSaveCmd extends BaseCmd {

    private List<ErrorDTO> errs;
}
