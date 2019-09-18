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
    private static final int FILTER = 1;
    private static final int NOT_FILTER = 0;

    @Test
    public void testCallBack() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PetStoreService.class);
        // a person or thing that stops or catches (someone or something) going from one place to another.
        enhancer.setCallback(new TransactionInterceptor());
        PetStoreService petStore = (PetStoreService) enhancer.create();
        petStore.getAccountDAO();
    }

    @Test
    public void testFilter() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PetStoreService.class);
        Callback[] callbacks = new Callback[]{new TransactionInterceptor(), NoOp.INSTANCE};
        Class<?>[] types = new Class<?>[callbacks.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = callbacks[i].getClass();
        }

        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackTypes(types);
        enhancer.setCallbackFilter(new ProxyCallbackFilter());

        PetStoreService petStoreService = (PetStoreService) enhancer.create();
        petStoreService.placeOrder();
        System.out.println(petStoreService.toString());

    }


    private static class TransactionInterceptor implements MethodInterceptor {
        TransactionManager txManager = new TransactionManager();

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            txManager.start();
            Object result = proxy.invokeSuper(obj, args);
            txManager.commit();
            return result;
        }
    }

    private class ProxyCallbackFilter implements CallbackFilter {
        @Override
        public int accept(Method method) {
            if (method.getName().startsWith("place")) {
                return NOT_FILTER;
            }

            return FILTER;
        }
    }
}
