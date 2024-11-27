package com.isylph.basis.repository.diff;

import com.isylph.basis.domain.domain.mark.Aggregate;
import com.isylph.basis.domain.domain.mark.Identifier;

import java.io.IOException;

public class ThreadLocalAggregateManager <T extends Aggregate<ID>, ID extends Identifier>
        implements AggregateManager<T, ID> {

    private ThreadLocal<DbContext<T, ID>> context;
    private Class<? extends T> targetClass;

    public ThreadLocalAggregateManager(Class<? extends T> targetClass) {
        this.targetClass = targetClass;
        this.context = ThreadLocal.withInitial(() -> new DbContext<>(targetClass));
    }

    public void attach(T aggregate) {
        try {
            context.get().attach(aggregate);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void attach(T aggregate, ID id) {
        context.get().setId(aggregate, id);
        try {
            context.get().attach(aggregate);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void detach(T aggregate) {
        context.get().detach(aggregate);
    }

    @Override
    public T find(ID id) {
        return context.get().find(id);
    }

    @Override
    public EntityDiff detectChanges(T aggregate) throws IllegalAccessException {
        try {
            return context.get().detectChanges(aggregate);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void merge(T aggregate) {
        try {
            context.get().merge(aggregate);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
