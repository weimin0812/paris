package com.free.paris.context.support;

import com.free.paris.beans.factory.annotation.AutowiredAnnotationProcessor;
import com.free.paris.beans.factory.config.ConfigurableBeanFactory;
import com.free.paris.beans.factory.support.DefaultBeanFactory;
import com.free.paris.beans.factory.xml.XmlBeanDefinitionReader;
import com.free.paris.context.ApplicationContext;
import com.free.paris.core.io.Resource;
import com.free.paris.util.ClassUtils;

public abstract class AbstractApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory = null;
    private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = getResourceByPath(configFile);
        reader.loadBeanDefinitions(resource);
        factory.setBeanClassLoader(getBeanClassLoader());
        registerBeanPostProcessor(factory);
    }

    private void registerBeanPostProcessor(ConfigurableBeanFactory factory) {
        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(factory);
        factory.addBeanPostProcessor(processor);
    }

    protected abstract Resource getResourceByPath(String path);

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;

    }

    public ClassLoader getBeanClassLoader() {
        return beanClassLoader == null ? ClassUtils.getDefaultClassLoader() : beanClassLoader;
    }

    @Override
    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }

    @Override
    public Class<?> getType(String name) {
        return factory.getType(name);
    }
}
