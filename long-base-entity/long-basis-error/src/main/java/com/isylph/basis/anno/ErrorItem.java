package com.isylph.basis.anno;


import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ErrorItem {

    String value();
}
