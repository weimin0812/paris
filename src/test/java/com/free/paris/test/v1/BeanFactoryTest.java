package com.free.paris.test.v1;

import com.free.paris.beans.BeanDefinition;
import com.free.paris.beans.factory.support.DefaultBeanFactory;
import com.free.paris.beans.factory.xml.XmlBeanDefinitionReader;
import com.free.paris.core.io.ClassPathResource;
import com.free.paris.service.v1.PetStoreService;
import com.free.paris.util.Constant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeanFactoryTest {
    private DefaultBeanFactory factory = null;
    private XmlBeanDefinitionReader reader = null;

    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean() {
        reader.loadBeanDefinitions(new ClassPathResource(Constant.petstoreV1));
        BeanDefinition bd = factory.getBeanDefinition(Constant.petStoreBeanId);
        assertTrue(bd.isSingleton());
        assertFalse(bd.isPrototype());
        assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());
        assertEquals("com.free.paris.service.v1.PetStoreService", bd.getBeanClassName());
        PetStoreService petStoreService = (PetStoreService) factory.getBean(Constant.petStoreBeanId);
        assertNotNull(petStoreService);
        PetStoreService petStoreService1 = (PetStoreService) factory.getBean(Constant.petStoreBeanId);
        assertTrue(petStoreService.equals(petStoreService1));
    }

}
