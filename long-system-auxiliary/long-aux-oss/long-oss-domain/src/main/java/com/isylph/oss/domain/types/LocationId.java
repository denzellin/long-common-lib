package com.isylph.oss.domain.types;

import lombok.Getter;

@Getter
public class LocationId {
    private Long id;

    public LocationId(Long id) {
        this.id = id;
    }
}
