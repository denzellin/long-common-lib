package com.isylph.basis.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class RetPage<T> implements Serializable {

    protected List<T> records;
    protected int total;
    protected int size;
    protected int current;

    public RetPage() {
        this.total = 0;
    }

    public RetPage(RetPage r) {
        this.total = r.getTotal();
        this.size = r.getSize();
        this.current = r.getCurrent();
    }

    public RetPage(List<T> records, int total, int size, int current) {
        this.records = records;
        this.total = total;
        this.size = size;
        this.current = current;
    }

    public RetPage(List<T> records, int total) {
        this.records = records;
        this.total = total;
    }
}
