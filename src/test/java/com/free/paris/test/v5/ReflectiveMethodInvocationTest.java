package com.free.paris.test.v5;

import com.free.paris.aop.aspectj.AspectJAfterReturningAdvice;
import com.free.paris.aop.aspectj.AspectJAfterThrowingAdvice;
import com.free.paris.aop.aspectj.AspectJBeforeAdvice;
import com.free.paris.aop.framework.ReflectiveMethodInvocation;
import com.free.paris.service.v5.PetStoreService;
import com.free.paris.tx.TransactionManager;
import com.free.paris.util.Constant;
import com.free.paris.util.MessageTracker;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectiveMethodInvocationTest {
    private AspectJBeforeAdvice beforeAdvice = null;
    private AspectJAfterReturningAdvice afterReturningAdvice = null;
    private AspectJAfterThrowingAdvice afterThrowingAdvice = null;

    private PetStoreService petStoreService = null;
    private TransactionManager tx;

    @Before
    public void setUp() throws NoSuchMethodException {
        petStoreService = new PetStoreService();
        tx = new TransactionManager();
        MessageTracker.clearMessages();

        beforeAdvice = new AspectJBeforeAdvice(null, TransactionManager.class.getMethod("start"), tx);
        afterReturningAdvice = new AspectJAfterReturningAdvice(null, TransactionManager.class.getMethod("commit"), tx);
        afterThrowingAdvice = new AspectJAfterThrowingAdvice(null, TransactionManager.class.getMethod("commit"), tx);
    }

    @Test
    public void testMethodInvocation() throws Throwable {
        Method targetMethod = PetStoreService.class.getMethod("placeOrder");
        List<MethodInterceptor> interceptors = new ArrayList<>();
        interceptors.add(beforeAdvice);
        interceptors.add(afterReturningAdvice);

        ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(petStoreService, targetMethod, new Object[0], interceptors);
        mi.proceed();
        List<String> messages = MessageTracker.getMessages();
        Assert.assertEquals(Constant.THREE, messages.size());
        Assert.assertEquals("start tx", messages.get(0));
        Assert.assertEquals("place order", messages.get(1));
        Assert.assertEquals("commit tx", messages.get(2));
    }

    @Test
    public void testAfterThrowing() throws NoSuchMethodException {
        Method placeOrderWithException = PetStoreService.class.getMethod("placeOrderWithException");
        List<MethodInterceptor> interceptors = new ArrayList<>();
        interceptors.add(beforeAdvice);
        interceptors.add(afterThrowingAdvice);
        ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(petStoreService, placeOrderWithException, new Object[0], interceptors);

        try {
            mi.proceed();
        } catch (Throwable throwable) {
            List<String> messages = MessageTracker.getMessages();
            Assert.assertEquals(2, messages.size());
            Assert.assertEquals("start tx", messages.get(0));
            Assert.assertEquals("commit tx", messages.get(1));
            return;
        }

        Assert.fail("No Exception thrown");
    }

}
