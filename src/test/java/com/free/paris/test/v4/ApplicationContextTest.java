package com.free.paris.test.v4;

import com.free.paris.context.ApplicationContext;
import com.free.paris.context.support.ClassPathXmlApplicationContext;
import com.free.paris.service.v4.PetStoreService;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextTest {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(Constant.petstoreV4);
        PetStoreService petStore = (PetStoreService) ctx.getBean(Constant.petStoreBeanId);

        Assert.assertNotNull(petStore.getAccountDAO());
        Assert.assertNotNull(petStore.getItemDAO());
    }
}
