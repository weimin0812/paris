package com.free.paris.test.v3;

import com.free.paris.beans.BeanDefinition;
import com.free.paris.beans.factory.support.ConstructorResolver;
import com.free.paris.beans.factory.support.DefaultBeanFactory;
import com.free.paris.beans.factory.xml.XmlBeanDefinitionReader;
import com.free.paris.core.io.ClassPathResource;
import com.free.paris.core.io.Resource;
import com.free.paris.service.v3.PetStoreService;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;

public class ConstructorResolverTest {
    @Test
    public void testAutowireConstructor() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource(Constant.petstoreV3);
        reader.loadBeanDefinitions(resource);
        BeanDefinition bd = factory.getBeanDefinition(Constant.petStoreBeanId);
        ConstructorResolver resolver = new ConstructorResolver(factory);
        PetStoreService petStoreService = (PetStoreService) resolver.autowireConstructor(bd);
        Assert.assertEquals(1, petStoreService.getVersion());
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
    }
}
