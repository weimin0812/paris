package com.free.paris.aop.config;

import com.free.paris.beans.factory.BeanFactory;
import com.free.paris.util.BeanUtils;
import com.free.paris.util.StringUtils;

import java.lang.reflect.Method;

public class MethodLocatingFactory {
    private String targetBeanName;

    private String methodName;

    private Method method;

    public void setBeanFactory(BeanFactory beanFactory) {
        if (!StringUtils.hasText(targetBeanName)) {
            throw new IllegalArgumentException("Property 'targetBeanName' is required");
        }

        if (!StringUtils.hasText(methodName)) {
            throw new IllegalArgumentException("Property 'methodName' is required");
        }

        Class<?> beanClass = beanFactory.getType(targetBeanName);
        if (beanClass == null) {
            throw new IllegalArgumentException("Can't determine type of bean with name '" + this.targetBeanName + "'");
        }

        method = BeanUtils.resolveSignature(this.methodName, beanClass);


    }

    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getTargetBeanName() {
        return targetBeanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Method getMethod() {
        return method;
    }
}
