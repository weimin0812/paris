package com.free.paris.beans.factory.support;

import com.free.paris.beans.BeanDefinition;

public interface BeanNameGenerator {
    String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);
}
