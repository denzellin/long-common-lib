package com.isylph.error.api.beans;


import com.isylph.basis.beans.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AuxErrorMessageDTO extends BaseVO {

    private Long id;

    private String module;

    private String name;

    private String annotation;

    private String message;

}
