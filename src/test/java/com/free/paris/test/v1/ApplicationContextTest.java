package com.free.paris.test.v1;

import com.free.paris.context.ApplicationContext;
import com.free.paris.context.support.ClassPathXmlApplicationContext;
import com.free.paris.context.support.FileSystemApplicationContext;
import com.free.paris.service.v1.PetStoreService;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextTest {
    @Test
    public void getBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(Constant.petstoreV1);
        PetStoreService petStore = (PetStoreService) ctx.getBean(Constant.petStoreBeanId);
        Assert.assertNotNull(petStore);
    }

    @Test
    public void testGetBeanFromFileSystemContext() {
        ApplicationContext ctx = new FileSystemApplicationContext(Constant.localPetstoreV1);
        PetStoreService petStore = (PetStoreService) ctx.getBean(Constant.petStoreBeanId);
        Assert.assertNotNull(petStore);
    }
}
