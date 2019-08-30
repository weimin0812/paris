package com.free.paris.test.v3;

import com.free.paris.context.ApplicationContext;
import com.free.paris.context.support.ClassPathXmlApplicationContext;
import com.free.paris.service.v3.PetStoreService;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextTestV3 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(Constant.petstoreV3);
        PetStoreService petStore = (PetStoreService) ctx.getBean(Constant.petStoreBeanId);

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
        Assert.assertEquals(1, petStore.getVersion());
    }

}
