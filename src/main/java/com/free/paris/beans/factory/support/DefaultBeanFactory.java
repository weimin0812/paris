package com.free.paris.beans.factory.support;

import com.free.paris.beans.BeanDefinition;
import com.free.paris.beans.PropertyValue;
import com.free.paris.beans.SimpleTypeConverter;
import com.free.paris.beans.factory.BeanCreationException;
import com.free.paris.beans.factory.config.BeanPostProcessor;
import com.free.paris.beans.factory.config.ConfigurableBeanFactory;
import com.free.paris.beans.factory.config.DependencyDescriptor;
import com.free.paris.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.free.paris.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory, BeanDefinitionRegistry {


    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(64);
    private ClassLoader beanClassLoader;
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

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
    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        this.beanPostProcessors.add(postProcessor);
    }

    @Override
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
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
        Object bean = instantiateBean(bd);
        populateBean(bd, bean);
        return bean;
    }

    private Object instantiateBean(BeanDefinition bd) {
        if (bd.hasConstructorArgumentValues()) {
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        } else {
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

    private void populateBean(BeanDefinition bd, Object bean) {
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            if (processor instanceof InstantiationAwareBeanPostProcessor) {
                ((InstantiationAwareBeanPostProcessor) processor).postProcessPropertyValues(bean, bd.getId());
            }
        }

        List<PropertyValue> pvs = bd.getPropertyValues();
        if (pvs == null && pvs.isEmpty()) {
            return;
        }

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyValue pv : pvs) {
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);

                for (PropertyDescriptor pd : pds) {
                    if (pd.getName().equals(propertyName)) {
                        Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, convertedValue);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object resolveDependency(DependencyDescriptor descriptor) {
        Class<?> typeToMatch = descriptor.getDependencyType();
        for (BeanDefinition bd : beanDefinitionMap.values()) {
            resolveBeanClass(bd);
            Class<?> beanClass = bd.getBeanClass();
            if (typeToMatch.isAssignableFrom(beanClass)) {
                return getBean(bd.getId());
            }
        }
        return null;
    }

    private void resolveBeanClass(BeanDefinition bd) {
        if (bd.hasBeanClass()) {
            return;
        }

        try {
            bd.resolveBeanClass(getBeanClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("can't load class:" + bd.getBeanClassName());
        }
    }
}
