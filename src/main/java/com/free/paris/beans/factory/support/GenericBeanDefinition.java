package com.free.paris.beans.factory.support;

import com.free.paris.beans.BeanDefinition;
import com.free.paris.beans.ConstructorArgument;
import com.free.paris.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

public class GenericBeanDefinition implements BeanDefinition {
    private String id;
    private String beanClassName;
    private boolean singleton = true;
    private boolean prototype = false;
    private String scope = SCOPE_DEFAULT;
    private List<PropertyValue> propertyValues = new ArrayList<>();
    private ConstructorArgument constructorArgument = new ConstructorArgument();
    private Class<?> beanClass;

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public GenericBeanDefinition() {

    }

    @Override
    public boolean isSingleton() {
        return singleton;
    }

    @Override
    public boolean isPrototype() {
        return prototype;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
        singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
        prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    @Override
    public String getBeanClassName() {
        return beanClassName;
    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ConstructorArgument getConstructorArgument() {
        return constructorArgument;
    }

    @Override
    public boolean hasConstructorArgumentValues() {
        return !constructorArgument.isEmpty();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    @Override
    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException {
        String beanClassName = getBeanClassName();
        if (beanClassName == null) {
            return null;
        }
        Class<?> resolvedClass = classLoader.loadClass(beanClassName);
        beanClass = resolvedClass;
        return resolvedClass;
    }

    @Override
    public Class<?> getBeanClass() throws IllegalArgumentException {
        if (this.beanClass == null) {
            throw new IllegalStateException(
                    "Bean class name [" + this.getBeanClassName() + "] has not been resolved into an actual Class");
        }
        return this.beanClass;
    }

    @Override
    public boolean hasBeanClass() {
        return beanClass != null;
    }
}
