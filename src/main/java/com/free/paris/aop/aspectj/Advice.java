package com.free.paris.aop.aspectj;

import com.free.paris.aop.Pointcut;
import org.aopalliance.intercept.MethodInterceptor;

public interface Advice extends MethodInterceptor {
    Pointcut getPointcut();
}
