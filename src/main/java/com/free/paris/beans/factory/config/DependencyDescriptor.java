package com.free.paris.beans.factory.config;

import com.free.paris.util.Assert;

import java.lang.reflect.Field;

public class DependencyDescriptor {
    private Field field;
    private boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull(field, "Filed must not be null");
        this.field = field;
        this.required = required;
    }

    public boolean isRequired() {
        return required;
    }

    public Class<?> getDependencyType() {
        if (field != null) {
            return field.getType();
        }

        throw new RuntimeException("only support field dependency");
    }

}
