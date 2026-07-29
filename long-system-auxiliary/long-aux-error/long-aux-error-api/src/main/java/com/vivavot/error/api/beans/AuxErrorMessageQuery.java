package com.vivavot.error.api.beans;


import com.vivavot.basis.base.BaseListQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class AuxErrorMessageQuery extends BaseListQuery {

    private String module;


}
