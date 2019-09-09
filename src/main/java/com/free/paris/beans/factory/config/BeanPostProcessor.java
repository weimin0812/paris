package com.free.paris.beans.factory.config;

import com.free.paris.beans.BeansException;

public interface BeanPostProcessor {
    Object beforeInitialization(Object bean, String beanName) throws BeansException;

    Object afterInitialization(Object bean, String beanName) throws BeansException;
}
