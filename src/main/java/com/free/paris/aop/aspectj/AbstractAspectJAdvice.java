package com.free.paris.aop.aspectj;

import com.free.paris.aop.Pointcut;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractAspectJAdvice implements Advice {
    protected AspectJExpressionPointcut pointcut;
    protected Method adviceMethod;
    protected Object adviceObject;

    public AbstractAspectJAdvice(AspectJExpressionPointcut pointcut, Method adviceMethod, Object adviceObject) {
        this.pointcut = pointcut;
        this.adviceMethod = adviceMethod;
        this.adviceObject = adviceObject;
    }

    public void invokeAdviceMethod() throws InvocationTargetException, IllegalAccessException {
        adviceMethod.invoke(adviceObject);
    }


    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
}
