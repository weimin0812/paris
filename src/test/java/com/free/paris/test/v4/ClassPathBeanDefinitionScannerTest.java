package com.free.paris.test.v4;

import com.free.paris.beans.BeanDefinition;
import com.free.paris.beans.factory.support.DefaultBeanFactory;
import com.free.paris.context.annotation.ScannedGenericBeanDefinition;
import com.free.paris.context.support.ClassPathBeanDefinitionScanner;
import com.free.paris.core.annotation.AnnotationAttributes;
import com.free.paris.core.type.AnnotationMetadata;
import com.free.paris.stereotype.Component;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ClassPathBeanDefinitionScannerTest {
    @Test
    public void testParseScannedBean() throws IOException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        String basePackages = Constant.basePackages;
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
        scanner.doScan(basePackages);

        String annotation = Component.class.getName();
        {
            BeanDefinition bd = factory.getBeanDefinition("petStore");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
            AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
            Assert.assertEquals("petStore", attributes.get("value"));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("accountDAO");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("itemDAO");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
    }
}
