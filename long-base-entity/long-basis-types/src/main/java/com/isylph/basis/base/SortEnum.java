package com.isylph.basis.base;

import lombok.Getter;

/**
 * <p>
 *
 * </p>
 *
 * @Author Denzel Lin
 * @Date 2026/5/11 17:25
 * @Version 1.0
 */
public enum SortEnum {
    ascend("ascend"),
    descend("descend");

    @Getter
    private final String sort;

    SortEnum(String sort) {
        this.sort = sort;
    }

    public static SortEnum of(String value) {
        for (SortEnum s : SortEnum.values()) {
            if (s.sort.equalsIgnoreCase(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid sort value: " + value);
    }

    @Override
    public String toString() {
        return sort;
    }
}
