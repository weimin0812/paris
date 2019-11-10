package com.free.paris.test.v5;

import com.free.paris.aop.config.MethodLocatingFactory;
import com.free.paris.beans.factory.support.DefaultBeanFactory;
import com.free.paris.beans.factory.xml.XmlBeanDefinitionReader;
import com.free.paris.core.io.ClassPathResource;
import com.free.paris.core.io.Resource;
import com.free.paris.tx.TransactionManager;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

public class MethodLocatingFactoryTest {
    @Test
    public void testGetMethod() throws NoSuchMethodException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource(Constant.petstoreV5);
        reader.loadBeanDefinitions(resource);

        MethodLocatingFactory methodLocatingFactory = new MethodLocatingFactory();
        methodLocatingFactory.setMethodName("start");
        methodLocatingFactory.setTargetBeanName("tx");
        methodLocatingFactory.setBeanFactory(factory);
        Method m = methodLocatingFactory.getObject();
        Assert.assertTrue(TransactionManager.class.equals(m.getDeclaringClass()));
        Assert.assertTrue(m.equals(TransactionManager.class.getMethod("start")));

    }
}
