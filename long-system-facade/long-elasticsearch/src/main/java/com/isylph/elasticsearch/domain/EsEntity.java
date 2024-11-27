package com.isylph.elasticsearch.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class EsEntity<T>  implements Serializable {
    protected String id;


    private T data;
}
