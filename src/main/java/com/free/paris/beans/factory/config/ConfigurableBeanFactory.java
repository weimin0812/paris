package com.free.paris.beans.factory.config;

import com.free.paris.beans.factory.BeanFactory;

public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {
    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();
}
