package com.free.paris.beans.factory.support;

import com.free.paris.beans.factory.BeanFactory;
import com.free.paris.beans.factory.config.RuntimeBeanReference;
import com.free.paris.beans.factory.config.TypedStringValue;

public class BeanDefinitionValueResolver {
    private final BeanFactory beanFactory;

    public BeanDefinitionValueResolver(
            BeanFactory beanFactory) {

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
