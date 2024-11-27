package com.isylph.error.api.beans;

import com.isylph.basis.base.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuxErrorMessageUpdateCmd extends BaseCmd {

    private Long id;

    private String message;


}
