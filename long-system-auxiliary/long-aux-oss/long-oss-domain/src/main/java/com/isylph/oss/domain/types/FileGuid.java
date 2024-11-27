package com.isylph.oss.domain.types;

import lombok.Getter;

@Getter
public class FileGuid {
    private String guid;

    public FileGuid(String guid) {
        this.guid = guid;
    }
}
