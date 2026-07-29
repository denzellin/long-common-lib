package com.vivavot.error.api.beans;


import com.vivavot.basis.base.BaseCmd;
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
