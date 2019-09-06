package com.free.paris.test.v4;

import com.free.paris.beans.factory.config.DependencyDescriptor;
import com.free.paris.beans.factory.support.DefaultBeanFactory;
import com.free.paris.beans.factory.xml.XmlBeanDefinitionReader;
import com.free.paris.core.io.ClassPathResource;
import com.free.paris.core.io.Resource;
import com.free.paris.dao.v4.AccountDAO;
import com.free.paris.service.v4.PetStoreService;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

public class DependencyDescriptorTest {
    @Test
    public void testResolveDependency() throws NoSuchFieldException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource(Constant.petstoreV4);
        reader.loadBeanDefinitions(resource);
        Field f = PetStoreService.class.getDeclaredField("accountDAO");
        DependencyDescriptor dependencyDescriptor = new DependencyDescriptor(f, true);
        Object o = factory.resolveDependency(dependencyDescriptor);
        Assert.assertTrue(o instanceof AccountDAO);
    }

}

