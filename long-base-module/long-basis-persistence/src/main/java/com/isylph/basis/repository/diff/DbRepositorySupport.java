package com.isylph.basis.repository.diff;

import com.isylph.basis.domain.domain.mark.Aggregate;
import com.isylph.basis.domain.domain.mark.Identifier;
import com.isylph.basis.domain.repository.Repository;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;

public abstract class DbRepositorySupport <T extends Aggregate<ID>, ID extends Identifier>
        implements Repository<T, ID> {

    @Getter
    private final Class<T> targetClass;

    // 让AggregateManager去维护Snapshot
    @Getter(AccessLevel.PROTECTED)
    private AggregateManager<T, ID> aggregateManager;

    protected DbRepositorySupport(Class<T> targetClass) {
        this.targetClass = targetClass;
        this.aggregateManager = AggregateManagerFactory.newInstance(targetClass);
    }

    /**
     * 这几个方法是继承的子类应该去实现的
     */
    protected abstract void onInsert(T aggregate);
    protected abstract T onSelect(ID id);
    protected abstract void onUpdate(T aggregate, EntityDiff diff);
    protected abstract void onDelete(T aggregate);

    /**
     * Attach的操作就是让Aggregate可以被追踪
     */
    @Override
    public void attach(@NotNull T aggregate) {
        this.aggregateManager.attach(aggregate);
    }

    /**
     * Detach的操作就是让Aggregate停止追踪
     */
    @Override
    public void detach(@NotNull T aggregate) {
        this.aggregateManager.detach(aggregate);
    }

    @Override
    public T find(@NotNull ID id) {
        T aggregate = this.onSelect(id);
        if (aggregate != null) {
            // 这里的就是让查询出来的对象能够被追踪。
            // 如果自己实现了一个定制查询接口，要记得单独调用attach。
            this.attach(aggregate);
        }
        return aggregate;
    }

    @Override
    public void remove(@NotNull T aggregate) {
        this.onDelete(aggregate);
        // 删除停止追踪
        this.detach(aggregate);
    }

    @Override
    public void save(@NotNull T aggregate) {
        // 如果没有ID，直接插入
        if (aggregate.getId() == null) {
            this.onInsert(aggregate);
            this.attach(aggregate);
            return;
        }

        // 做Diff
        EntityDiff diff = null;
        try {
            diff = aggregateManager.detectChanges(aggregate);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (diff.isEmpty()) {
            return;
        }

        // 调用UPDATE
        this.onUpdate(aggregate, diff);

        // 最终将DB带来的变化更新回AggregateManager
        aggregateManager.merge(aggregate);
    }


}
