package com.free.paris.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice {

    public AspectJAfterReturningAdvice(AspectJExpressionPointcut pointcut, Method adviceMethod, Object adviceObject) {
        super(pointcut, adviceMethod, adviceObject);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object o = mi.proceed();
        invokeAdviceMethod();
        return o;
    }
}
