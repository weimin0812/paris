package com.free.paris.aop.framework;

import com.free.paris.aop.Advice;
import com.free.paris.util.Assert;
import org.springframework.cglib.core.CodeGenerationException;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class CglibProxyFactory implements AopProxyFactory {
    private static final int AOP_PROXY = 0;
    private static final int INVOKE_TARGET = 1;
    private static final int NO_OVERRIDE = 2;
    private static final int DISPATCH_TARGET = 3;
    private static final int DISPATCH_ADVISED = 4;
    private static final int INVOKE_EQUALS = 5;
    private static final int INVOKE_HASHCODE = 6;

    protected final AopConfig config;

    public CglibProxyFactory(AopConfig config) throws AopConfigException {
        Assert.notNull(config, "AdvisedSupport must not be null");
        if (config.getAdvices().size() == 0 /*&& config.getTargetSource() == AdvisedSupport.EMPTY_TARGET_SOURCE*/) {
            throw new AopConfigException("No advisors and no TargetSource specified");
        }
        this.config = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(null);
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        try {
            Class<?> rootClass = config.getTargetClass();
            Enhancer enhancer = new Enhancer();
            if (classLoader != null) {
                enhancer.setClassLoader(classLoader);
            }
            enhancer.setSuperclass(rootClass);
            enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
            enhancer.setInterceptDuringConstruction(false);
            Callback[] callbacks = getCallbacks(rootClass);
            Class<?>[] types = new Class<?>[callbacks.length];
            for (int i = 0; i < types.length; i++) {
                types[i] = callbacks[i].getClass();
            }
            enhancer.setCallbackFilter(new ProxyCallbackFilter(config));
            enhancer.setCallbackTypes(types);
            enhancer.setCallbacks(callbacks);
            Object proxy = enhancer.create();
            return proxy;
        } catch (CodeGenerationException ex) {
            throw new AopConfigException("Could not generate CGLIB subclass of class [" +
                    this.config.getTargetClass() + "]: " +
                    "Common causes of this problem include using a final class or a non-visible class",
                    ex);
        } catch (IllegalArgumentException ex) {
            throw new AopConfigException("Could not generate CGLIB subclass of class [" +
                    this.config.getTargetClass() + "]: " +
                    "Common causes of this problem include using a final class or a non-visible class",
                    ex);
        } catch (Exception ex) {
            // TargetSource.getTarget() failed
            throw new AopConfigException("Unexpected AOP exception", ex);
        }
    }

    private Callback[] getCallbacks(Class<?> rootClass) {
        Callback aopInterceptor = new DynamicAdvisedInterceptor(this.config);
        Callback[] callbacks = new Callback[]{
                aopInterceptor,  // AOP_PROXY for normal advice
        };
        return callbacks;
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor, Serializable {

        private final AopConfig config;

        public DynamicAdvisedInterceptor(AopConfig advised) {
            this.config = advised;
        }

        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Object target = this.config.getTargetObject();
            List<Advice> chain = this.config.getAdvices(method/*, targetClass*/);
            Object retVal;
            if (chain.isEmpty() && Modifier.isPublic(method.getModifiers())) {
                retVal = methodProxy.invoke(target, args);
            } else {
                List<org.aopalliance.intercept.MethodInterceptor> interceptors =
                        new ArrayList<org.aopalliance.intercept.MethodInterceptor>();
                interceptors.addAll(chain);
                retVal = new ReflectiveMethodInvocation(target, method, args, interceptors).proceed();
            }

            return retVal;
        }
    }

    private static class ProxyCallbackFilter implements CallbackFilter {
        private final AopConfig config;

        public ProxyCallbackFilter(AopConfig advised) {
            this.config = advised;

        }

        public int accept(Method method) {
            return AOP_PROXY;
        }
    }
}
