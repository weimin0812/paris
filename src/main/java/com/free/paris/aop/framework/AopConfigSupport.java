package com.free.paris.aop.framework;

import com.free.paris.aop.Pointcut;
import com.free.paris.aop.aspectj.Advice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AopConfigSupport implements AopConfig {
    private boolean proxyTargetClass = false;
    private Object targetObject = null;
    private List<Advice> advices = new ArrayList<>();
    private List<Class> interfaces = new ArrayList<>();


    @Override
    public Class<?> getTargetClass() {
        return targetObject.getClass();
    }

    @Override
    public Object getTargetObject() {
        return targetObject;
    }

    @Override
    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    @Override
    public Class<?>[] getProxiedInterfaces() {
        return interfaces.toArray(new Class[interfaces.size()]);
    }

    @Override
    public boolean isInterfaceProxied(Class<?> intf) {
        return interfaces.stream().anyMatch(i -> i.isAssignableFrom(intf));
    }

    @Override
    public List<Advice> getAdvices() {
        return advices;
    }

    @Override
    public void addAdvice(Advice advice) {
        advices.add(advice);
    }

    @Override
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

    @Override
    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }
}
