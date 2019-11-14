package com.free.paris.aop.framework;

import com.free.paris.aop.Advice;
import com.free.paris.aop.Pointcut;
import com.free.paris.util.Assert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AopConfigSupport implements AopConfig {
    private boolean proxyTargetClass = false;

    private Object targetObject = null;

    private List<Advice> advices = new ArrayList<Advice>();

    private List<Class> interfaces = new ArrayList<Class>();

    public AopConfigSupport() {

    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public Object getTargetObject() {
        return this.targetObject;
    }

    public Class<?> getTargetClass() {
        return this.targetObject.getClass();
    }

    public void addInterface(Class<?> intf) {
        Assert.notNull(intf, "Interface must not be null");
        if (!intf.isInterface()) {
            throw new IllegalArgumentException("[" + intf.getName() + "] is not an interface");
        }
        if (!this.interfaces.contains(intf)) {
            this.interfaces.add(intf);
        }
    }

    public Class<?>[] getProxiedInterfaces() {
        return this.interfaces.toArray(new Class[this.interfaces.size()]);
    }

    public boolean isInterfaceProxied(Class<?> intf) {
        for (Class proxyIntf : this.interfaces) {
            if (intf.isAssignableFrom(proxyIntf)) {
                return true;
            }
        }
        return false;
    }

    public void addAdvice(Advice advice) {
        this.advices.add(advice);
    }

    public boolean isProxyTargetClass() {

        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public List<Advice> getAdvices() {

        return this.advices;
    }

    public List<Advice> getAdvices(Method method) {
        List<Advice> result = new ArrayList<Advice>();
        for (Advice advice : this.getAdvices()) {
            Pointcut pc = advice.getPointcut();
            if (pc.getMethodMatcher().matches(method)) {
                result.add(advice);
            }
        }
        return result;
    }
}
