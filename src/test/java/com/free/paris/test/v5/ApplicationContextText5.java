package com.free.paris.test.v5;

import com.free.paris.context.ApplicationContext;
import com.free.paris.context.support.ClassPathXmlApplicationContext;
import com.free.paris.service.v5.PetStoreService;
import com.free.paris.util.Constant;
import com.free.paris.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ApplicationContextText5 {
    @Before
    public void setUp() {
        MessageTracker.clearMsgs();
    }

    @Test
    public void testPlaceOrder() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(Constant.petstoreV5);
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStoreService");
        Assert.assertNotNull(petStore.getAccountDAO());
        Assert.assertNotNull(petStore.getItemDAO());
        petStore.placeOrder();
        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));
    }

}
