package com.isylph.basis.types;

import com.isylph.basis.base.BaseType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@Getter
public class FileData extends BaseType {

    private String guid;

    private String url;

    private String name;

    public FileData(String guid, String url, String name) {
        this.guid = guid;
        this.url = url;
        this.name = name;
    }
}
