package com.isylph.basis.types;

import com.isylph.basis.base.BaseType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class Url extends BaseType {

    String url;

    public Url(String url) {
        // TODO 非法字符过滤
        this.url = url;
    }
}
