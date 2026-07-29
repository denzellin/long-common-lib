package com.vivavot.basis.repository.diff;

import com.vivavot.basis.domain.domain.mark.Aggregate;
import com.vivavot.basis.domain.domain.mark.Identifier;

public class AggregateManagerFactory {

    public static <ID extends Identifier, T extends Aggregate<ID>>
    AggregateManager<T, ID> newInstance(Class<T> targetClass) {
        return new ThreadLocalAggregateManager<T, ID>(targetClass);
    }
}
