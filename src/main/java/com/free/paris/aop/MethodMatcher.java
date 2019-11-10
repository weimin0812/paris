package com.free.paris.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {
    boolean matches(Method method);
}
