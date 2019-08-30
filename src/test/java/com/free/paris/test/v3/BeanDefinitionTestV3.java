package com.free.paris.test.v3;

import com.free.paris.beans.ConstructorArgument;
import com.free.paris.beans.BeanDefinition;
import com.free.paris.beans.factory.config.RuntimeBeanReference;
import com.free.paris.beans.factory.support.DefaultBeanFactory;
import com.free.paris.beans.factory.config.TypedStringValue;
import com.free.paris.beans.factory.xml.XmlBeanDefinitionReader;
import com.free.paris.core.io.ClassPathResource;
import com.free.paris.core.io.Resource;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BeanDefinitionTestV3 {

    @Test
    public void testConstructorArgument() {

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource(Constant.petstoreV3);
        reader.loadBeanDefinitions(resource);

        BeanDefinition bd = factory.getBeanDefinition(Constant.petStoreBeanId);
        Assert.assertEquals("com.free.paris.service.v3.PetStoreService", bd.getBeanClassName());

        ConstructorArgument args = bd.getConstructorArgument();
        List<ConstructorArgument.ValueHolder> valueHolders = args.getArgumentValues();

        Assert.assertEquals(3, valueHolders.size());

        RuntimeBeanReference ref1 = (RuntimeBeanReference) valueHolders.get(0).getValue();
        Assert.assertEquals("accountDao", ref1.getBeanName());
        RuntimeBeanReference ref2 = (RuntimeBeanReference) valueHolders.get(1).getValue();
        Assert.assertEquals("itemDao", ref2.getBeanName());

        TypedStringValue strValue = (TypedStringValue) valueHolders.get(2).getValue();
        Assert.assertEquals("1", strValue.getValue());
    }

}
