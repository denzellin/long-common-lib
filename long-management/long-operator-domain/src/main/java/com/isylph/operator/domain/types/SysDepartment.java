package com.isylph.operator.domain.types;

import com.isylph.basis.base.BaseType;
import lombok.Getter;

@Getter
public class SysDepartment extends BaseType {

    private Long id;

    private String name;

    private String code;

    public SysDepartment(Long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }
}
