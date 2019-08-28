package com.free.paris.test.v2;

import com.free.paris.context.ApplicationContext;
import com.free.paris.context.support.ClassPathXmlApplicationContext;
import com.free.paris.dao.v2.AccountDao;
import com.free.paris.dao.v2.ItemDao;
import com.free.paris.service.v2.PetStoreService;
import com.free.paris.util.Constant;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicationContextTestV2 {
    @Test
    public void testGetBeanProperty() {
        ApplicationContext context = new ClassPathXmlApplicationContext(Constant.petstoreV2);
        PetStoreService petStore = (PetStoreService) context.getBean(Constant.petStoreBeanId);
        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());
        assertTrue(petStore.getAccountDao() instanceof AccountDao);
        assertTrue(petStore.getItemDao() instanceof ItemDao);

        assertEquals(Constant.shenxiu, petStore.getOwner());
        assertEquals(2, petStore.getVersion());

    }
}
