package com.free.paris.aop;

public interface Pointcut {
    MethodMatcher getMethodMatcher();

    String getExpression();
}
