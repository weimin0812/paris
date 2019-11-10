package com.free.paris.aop.aspectj;

import com.free.paris.aop.Advice;
import com.free.paris.aop.config.AspectInstanceFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractAspectJAdvice implements Advice {
    protected Method adviceMethod;
    protected AspectJExpressionPointcut pointcut;
    protected AspectInstanceFactory adviceObjectFactory;

    public AbstractAspectJAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObjectFactory) {
        this.adviceMethod = adviceMethod;
        this.pointcut = pointcut;
        this.adviceObjectFactory = adviceObjectFactory;
    }

    public void invokeAdviceMethod() throws InvocationTargetException, IllegalAccessException {
        adviceMethod.invoke(adviceObjectFactory.getAspectInstance());
    }

    @Override
    public AspectJExpressionPointcut getPointcut() {
        return pointcut;
    }

    public Method getAdviceMethod() {
        return adviceMethod;
    }

    public Object getAdviceInstance() {
        return adviceObjectFactory.getAspectInstance();
    }
}
