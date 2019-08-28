package com.free.paris.test.v2;

import com.free.paris.beans.factory.config.RuntimeBeanReference;
import com.free.paris.beans.factory.support.BeanDefinitionValueResolver;
import com.free.paris.beans.factory.support.DefaultBeanFactory;
import com.free.paris.beans.factory.support.TypedStringValue;
import com.free.paris.beans.factory.xml.XmlBeanDefinitionReader;
import com.free.paris.core.io.ClassPathResource;
import com.free.paris.dao.v2.AccountDao;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;

public class BeanDefinitionValueResolverTest {
    @Test
    public void testResolveRuntimeBeanReference() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource(Constant.petstoreV2));
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);
        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(reference);
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    @Test
    public void testResolveTypedStringValue() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);

        TypedStringValue stringValue = new TypedStringValue("test");
        Object value = resolver.resolveValueIfNecessary(stringValue);
        Assert.assertNotNull(value);
        Assert.assertEquals("test", value);

    }


}
