package com.free.paris.aop.config;

import com.free.paris.beans.BeansException;
import com.free.paris.beans.factory.BeanFactory;
import com.free.paris.beans.factory.BeanFactoryAware;
import com.free.paris.util.StringUtils;

public class AspectInstanceFactory implements BeanFactoryAware {
    private String aspectBeanName;

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        if (!StringUtils.hasText(this.aspectBeanName)) {
            throw new IllegalArgumentException("'aspectBeanName' is required");
        }
    }

    public void setAspectBeanName(String aspectBeanName) {
        this.aspectBeanName = aspectBeanName;
    }

    public Object getAspectInstance() {
        return beanFactory.getBean(aspectBeanName);
    }
}
