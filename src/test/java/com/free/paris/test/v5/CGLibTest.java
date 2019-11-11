package com.free.paris.test.v5;

import com.free.paris.service.v5.PetStoreService;
import com.free.paris.tx.TransactionManager;
import org.junit.Test;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.proxy.NoOp;

import java.lang.reflect.Method;

public class CGLibTest {
    @Test
    public void testCallback() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PetStoreService.class);
        enhancer.setCallback(new TransactionInterceptor());
        PetStoreService petStoreService = (PetStoreService) enhancer.create();
        petStoreService.placeOrder();
    }

    @Test
    public void testFilter() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PetStoreService.class);
        enhancer.setInterceptDuringConstruction(false);
        Callback[] callbacks = new Callback[]{new TransactionInterceptor(), NoOp.INSTANCE};
        Class<?>[] types = new Class<?>[callbacks.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = callbacks[i].getClass();
        }
        enhancer.setCallbackFilter(new ProxyCallbackFilter());
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackTypes(types);
        PetStoreService petStoreService = (PetStoreService) enhancer.create();
        petStoreService.placeOrder();
        System.out.println(petStoreService.toString());
    }


    public static class TransactionInterceptor implements MethodInterceptor {

        TransactionManager txManager = new TransactionManager();

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            txManager.start();
            Object result = methodProxy.invokeSuper(obj, args);
            txManager.commit();
            return result;
        }
    }

    public static class ProxyCallbackFilter implements CallbackFilter {
        @Override
        public int accept(Method method) {
            return method.getName().startsWith("place") ? 0 : 1;
        }
    }


}
