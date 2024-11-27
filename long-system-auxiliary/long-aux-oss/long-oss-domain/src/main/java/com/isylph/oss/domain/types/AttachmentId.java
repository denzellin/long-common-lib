package com.isylph.oss.domain.types;

import lombok.Getter;

@Getter
public class AttachmentId {
    private Long id;

    public AttachmentId(Long id) {
        this.id = id;
    }
}
