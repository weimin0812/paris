package com.free.paris.test.v5;

import com.free.paris.aop.aspectj.AspectJAfterReturningAdvice;
import com.free.paris.aop.aspectj.AspectJBeforeAdvice;
import com.free.paris.aop.aspectj.AspectJExpressionPointcut;
import com.free.paris.aop.config.AspectInstanceFactory;
import com.free.paris.aop.framework.AopConfig;
import com.free.paris.aop.framework.AopConfigSupport;
import com.free.paris.aop.framework.CglibProxyFactory;
import com.free.paris.beans.factory.BeanFactory;
import com.free.paris.service.v5.PetStoreService;
import com.free.paris.util.Constant;
import com.free.paris.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CGLibAopProxyTest extends AbstractV5Test {
    private AspectJBeforeAdvice beforeAdvice = null;
    private AspectJAfterReturningAdvice afterAdvice = null;
    private AspectJExpressionPointcut pc = null;
    private BeanFactory beanFactory = null;
    private AspectInstanceFactory aspectInstanceFactory = null;

    @Before
    public void setUp() throws NoSuchMethodException {
        MessageTracker.clearMsgs();
        String expression = "execution(* com.free.paris.service.v5.*.placeOrder(..))";
        pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);
        beanFactory = getBeanFactory(Constant.petstoreV5);
        aspectInstanceFactory = getAspectInstanceFactory(Constant.TX);
        aspectInstanceFactory.setBeanFactory(beanFactory);
        beforeAdvice = new AspectJBeforeAdvice(
                getAdviceMethod("start"),
                pc,
                aspectInstanceFactory
        );
        afterAdvice = new AspectJAfterReturningAdvice(
                getAdviceMethod("commit"),
                pc,
                aspectInstanceFactory
        );
    }

    @Test
    public void testGetProxy() {
        AopConfig config = new AopConfigSupport();
        config.addAdvice(beforeAdvice);
        config.addAdvice(afterAdvice);
        config.setTargetObject(new PetStoreService());
        CglibProxyFactory proxyFactory = new CglibProxyFactory(config);
        PetStoreService proxy = (PetStoreService) proxyFactory.getProxy();
        proxy.placeOrder();


        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));

        System.out.println(proxy.toString());

    }

}
