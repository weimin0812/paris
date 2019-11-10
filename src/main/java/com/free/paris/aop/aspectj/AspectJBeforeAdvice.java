package com.free.paris.aop.aspectj;

import com.free.paris.aop.config.AspectInstanceFactory;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class AspectJBeforeAdvice extends AbstractAspectJAdvice {

    public AspectJBeforeAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObjectFactory) {
        super(adviceMethod, pointcut, adviceObjectFactory);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        invokeAdviceMethod();
        return invocation.proceed();
    }
}
