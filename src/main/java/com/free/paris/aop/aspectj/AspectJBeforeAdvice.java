package com.free.paris.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class AspectJBeforeAdvice extends AbstractAspectJAdvice {


    public AspectJBeforeAdvice(AspectJExpressionPointcut pointcut, Method adviceMethod, Object adviceObject) {
        super(pointcut, adviceMethod, adviceObject);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        invokeAdviceMethod();
        Object o = mi.proceed();
        return o;
    }
}
