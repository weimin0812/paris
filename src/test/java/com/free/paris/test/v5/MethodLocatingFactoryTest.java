package com.free.paris.test.v5;

import com.free.paris.aop.config.MethodLocatingFactory;
import com.free.paris.beans.factory.support.DefaultBeanFactory;
import com.free.paris.beans.factory.xml.XmlBeanDefinitionReader;
import com.free.paris.core.io.ClassPathResource;
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
        reader.loadBeanDefinitions(new ClassPathResource(Constant.petstoreV5));
        MethodLocatingFactory methodLocatingFactory = new MethodLocatingFactory();
        methodLocatingFactory.setTargetBeanName("tx");
        methodLocatingFactory.setMethodName("start");
        methodLocatingFactory.setBeanFactory(factory);

        Method method = methodLocatingFactory.getMethod();
        Assert.assertTrue(TransactionManager.class.equals(method.getDeclaringClass()));
        Assert.assertTrue(method.equals(TransactionManager.class.getMethod("start")));

    }
}
