package com.free.paris.context.support;

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
}
