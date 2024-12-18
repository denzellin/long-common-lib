package com.isylph.basis.repository.diff;

import com.isylph.basis.domain.domain.mark.Aggregate;
import com.isylph.basis.domain.domain.mark.Identifier;

import java.lang.reflect.Field;

public class ReflectionUtils {

    public static <T extends Aggregate<ID>, ID extends Identifier> void writeField(
            String fieldName, T aggregate, ID id) {
        Class<? extends Aggregate> aClass = aggregate.getClass();
        try {
            Field field = aClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(aggregate, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
