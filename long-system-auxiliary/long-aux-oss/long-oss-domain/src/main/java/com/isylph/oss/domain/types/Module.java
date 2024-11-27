package com.isylph.oss.domain.types;


import lombok.Getter;

@Getter
public class Module {
    private String module;

    public Module(String module) {
        this.module = module;
    }
}
