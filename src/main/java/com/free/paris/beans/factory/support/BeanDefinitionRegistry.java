package com.free.paris.beans.factory.support;

import com.free.paris.beans.BeanDefinition;

public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String beanId);

    void registerBeanDefinition(String beanId, BeanDefinition beanDefinition);
}
