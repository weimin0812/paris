package com.free.paris.beans.factory.support;

import com.free.paris.beans.factory.BeanCreationException;
import com.free.paris.beans.factory.BeanDefinition;
import com.free.paris.beans.factory.config.ConfigurableBeanFactory;
import com.free.paris.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory, BeanDefinitionRegistry {


    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(64);
    private ClassLoader beanClassLoader;

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        return beanDefinitionMap.get(beanId);
    }

    @Override
    public void registerBeanDefinition(String beanId, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanId, beanDefinition);
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return beanClassLoader == null ? ClassUtils.getDefaultClassLoader() : beanClassLoader;
    }

    @Override
    public Object getBean(String beanId) {
        BeanDefinition bd = beanDefinitionMap.get(beanId);
        if (bd == null) {
            return null;
        }

        if (bd.isSingleton()) {
            Object bean = getSingleton(beanId);
            if (bean == null) {
                bean = createBean(bd);
                registerSingleton(beanId, bean);
            }

            return bean;
        }

        return createBean(bd);
    }

    private Object createBean(BeanDefinition bd) {
        ClassLoader cl = getBeanClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " failed", e);
        }

    }
}
