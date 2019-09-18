package com.free.paris.aop.framework;

public interface AopProxyFactory {
    Object getProxy();

    Object getProxy(ClassLoader classLoader) throws Exception;
}
