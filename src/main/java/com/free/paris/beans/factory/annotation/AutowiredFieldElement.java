package com.free.paris.beans.factory.annotation;

import com.free.paris.beans.factory.BeanCreationException;
import com.free.paris.beans.factory.config.AutowireCapableBeanFactory;
import com.free.paris.beans.factory.config.DependencyDescriptor;
import com.free.paris.util.ReflectionUtils;

import java.lang.reflect.Field;

public class AutowiredFieldElement extends InjectionElement {
    boolean required;

    public AutowiredFieldElement(Field f, boolean required, AutowireCapableBeanFactory factory) {
        super(f, factory);
        this.required = required;
    }

    public Field getField() {
        return (Field) member;
    }

    @Override
    public void inject(Object target) {
        Field field = getField();

        try {
            DependencyDescriptor descriptor = new DependencyDescriptor(field, required);
            Object value = factory.resolveDependency(descriptor);
            if (value != null) {
                ReflectionUtils.makeAccessible(field);
                field.set(target, value);
            }
        } catch (Exception e) {
            throw new BeanCreationException("Could not autowire field: " + field, e);
        }
    }
}
