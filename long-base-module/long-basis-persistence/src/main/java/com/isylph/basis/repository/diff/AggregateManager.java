package com.isylph.basis.repository.diff;

import com.isylph.basis.domain.domain.mark.Identifier;

public interface AggregateManager <T, ID extends Identifier> {
    void attach(T aggregate);

    void attach(T aggregate, ID id);

    void detach(T aggregate);

    EntityDiff detectChanges(T aggregate) throws IllegalAccessException;

    void merge(T aggregate);

    T find(ID id);
}
