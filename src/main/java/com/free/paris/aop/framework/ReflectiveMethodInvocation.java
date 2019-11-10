package com.free.paris.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
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

    public final Object getThis() {
        return this.targetObject;
    }


    /**
     * Return the method invoked on the proxied interface.
     * May or may not correspond with a method invoked on an underlying
     * implementation of that interface.
     */
    public final Method getMethod() {
        return this.targetMethod;
    }

    public final Object[] getArguments() {
        return (this.arguments != null ? this.arguments : new Object[0]);
    }



    public Object proceed() throws Throwable {
        //	所有的拦截器已经调用完成
        if (this.currentInterceptorIndex == this.interceptors.size() - 1) {
            return invokeJoinpoint();
        }

        this.currentInterceptorIndex ++;

        MethodInterceptor interceptor =
                this.interceptors.get(this.currentInterceptorIndex);

        return interceptor.invoke(this);

    }

    /**
     * Invoke the joinpoint using reflection.
     * Subclasses can override this to use custom invocation.
     * @return the return value of the joinpoint
     * @throws Throwable if invoking the joinpoint resulted in an exception
     */
    protected Object invokeJoinpoint() throws Throwable {
        return this.targetMethod.invoke(this.targetObject, this.arguments);
    }

    public AccessibleObject getStaticPart() {
        return this.targetMethod;
    }


}
