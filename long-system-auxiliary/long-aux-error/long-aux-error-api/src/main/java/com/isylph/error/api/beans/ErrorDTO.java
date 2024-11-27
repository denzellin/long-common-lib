package com.isylph.error.api.beans;

import com.isylph.basis.beans.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ErrorDTO extends BaseDTO {

    private String module;

    private String name;

    private Long val;

    private String annotation;

    private String message;
}
