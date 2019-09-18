package com.free.paris.beans.factory;

public interface BeanFactory {
    Object getBean(String beanId);

    Class<?> getType(String beanName);
}
