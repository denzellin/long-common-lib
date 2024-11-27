package com.isylph.department.domain.types;

import com.isylph.basis.base.BaseType;
import lombok.Getter;


@Getter
public class Operator extends BaseType {

    private Long id;

    private String name;

    public Operator(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
