package com.free.paris.test.v4;

import com.free.paris.beans.factory.annotation.AutowiredFieldElement;
import com.free.paris.beans.factory.annotation.InjectionElement;
import com.free.paris.beans.factory.annotation.InjectionMetadata;
import com.free.paris.beans.factory.support.DefaultBeanFactory;
import com.free.paris.beans.factory.xml.XmlBeanDefinitionReader;
import com.free.paris.core.io.ClassPathResource;
import com.free.paris.dao.v4.AccountDAO;
import com.free.paris.dao.v4.ItemDAO;
import com.free.paris.service.v4.PetStoreService;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class InjectionMetadataTest {
    @Test
    public void testInjection() throws NoSuchFieldException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        ClassPathResource resource = new ClassPathResource(Constant.petstoreV4);
        reader.loadBeanDefinitions(resource);

        LinkedList<InjectionElement> elements = new LinkedList<>();
        Class<PetStoreService> petStoreServiceClass = PetStoreService.class;
        Field accountDaoField = petStoreServiceClass.getDeclaredField(Constant.ACCOUNT_DAO);
        InjectionElement accountDaoInjectionElement = new AutowiredFieldElement(accountDaoField, true, factory);
        elements.add(accountDaoInjectionElement);

        Field itemDaoField = petStoreServiceClass.getDeclaredField(Constant.ITEM_DAO);
        InjectionElement itemDaoInjectionElement = new AutowiredFieldElement(itemDaoField, true, factory);
        elements.add(itemDaoInjectionElement);

        InjectionMetadata injectionMetadata = new InjectionMetadata(petStoreServiceClass, elements);
        PetStoreService petStoreService = new PetStoreService();
        injectionMetadata.inject(petStoreService);
        Assert.assertTrue(petStoreService.getItemDAO() instanceof ItemDAO);
        Assert.assertTrue(petStoreService.getAccountDAO() instanceof AccountDAO);
    }

}
