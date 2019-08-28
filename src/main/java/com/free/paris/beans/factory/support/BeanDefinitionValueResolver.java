package com.free.paris.beans.factory.support;

import com.free.paris.beans.factory.config.RuntimeBeanReference;

public class BeanDefinitionValueResolver {
    private final DefaultBeanFactory beanFactory;

    public BeanDefinitionValueResolver(
            DefaultBeanFactory beanFactory) {

        this.beanFactory = beanFactory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String refName = ref.getBeanName();
            Object bean = beanFactory.getBean(refName);
            return bean;
        }

        if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        }

        throw new RuntimeException("the value " + value + " has not implemented");
    }


}
