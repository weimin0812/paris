package com.free.paris.test.v5;

import com.free.paris.aop.aspectj.AspectJAfterReturningAdvice;
import com.free.paris.aop.aspectj.AspectJBeforeAdvice;
import com.free.paris.aop.aspectj.AspectJExpressionPointcut;
import com.free.paris.aop.framework.AopConfig;
import com.free.paris.aop.framework.AopConfigSupport;
import com.free.paris.aop.framework.CglibProxyFactory;
import com.free.paris.service.v5.PetStoreService;
import com.free.paris.tx.TransactionManager;
import com.free.paris.util.MessageTracker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

@Slf4j
public class CglibAopProxyTest {
    private static AspectJBeforeAdvice beforeAdvice = null;
    private static AspectJAfterReturningAdvice afterAdvice = null;
    private static AspectJExpressionPointcut pc = null;

    private TransactionManager tx;

    @Before
    public void setUp() throws NoSuchMethodException {
        tx = new TransactionManager();
        String expression = "execution(* com.free.paris.service.v5.*.placeOrder(..))";
        pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);
        beforeAdvice = new AspectJBeforeAdvice(pc,
                TransactionManager.class.getMethod("start"),
                tx
        );

        afterAdvice = new AspectJAfterReturningAdvice(pc,
                TransactionManager.class.getMethod("commit"),
                tx
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
        List<String> msgs = MessageTracker.getMessages();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));

        log.info("{}", proxy.toString());

    }

}
