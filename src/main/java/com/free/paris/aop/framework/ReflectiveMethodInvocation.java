package com.free.paris.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectiveMethodInvocation implements MethodInvocation {
    protected final Object targetObject;
    protected final Method targetMethod;
    protected Object[] arguments;
    protected final List<MethodInterceptor> interceptors;
    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object targetObject, Method targetMethod, Object[] arguments, List<MethodInterceptor> interceptors) {
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.arguments = arguments;
        this.interceptors = interceptors;
    }

    @Override
    public Object getThis() {
        return targetObject;
    }

    @Override
    public Method getMethod() {
        return targetMethod;
    }

    @Override
    public Object[] getArguments() {
        return arguments != null ? arguments : new Object[0];
    }

    @Override
    public AccessibleObject getStaticPart() {
        return targetMethod;
    }

    @Override
    public Object proceed() throws Throwable {
        if (currentInterceptorIndex == interceptors.size() - 1) {
            return invokeJoinpoint();
        }
        currentInterceptorIndex++;
        MethodInterceptor interceptor = interceptors.get(currentInterceptorIndex);

        return interceptor.invoke(this);
    }

    private Object invokeJoinpoint() throws InvocationTargetException, IllegalAccessException {
        return targetMethod.invoke(targetObject, arguments);
    }


}
