package com.free.paris.test.v4;

import com.free.paris.core.annotation.AnnotationAttributes;
import com.free.paris.core.io.ClassPathResource;
import com.free.paris.core.type.AnnotationMetadata;
import com.free.paris.core.type.classreading.MetadataReader;
import com.free.paris.core.type.classreading.SimpleMetadataReader;
import com.free.paris.stereotype.Component;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class MetadataReaderTest {
    @Test
    public void testGetMetadata() throws IOException {
        ClassPathResource resource = new ClassPathResource(Constant.v4PetStoreServiceClassPath);
        MetadataReader reader = new SimpleMetadataReader(resource);
        AnnotationMetadata amd = reader.getAnnotationMetadata();
        String annotation = Component.class.getName();

        Assert.assertTrue(amd.hasAnnotation(annotation));
        AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attributes.get("value"));

        //注：下面对class metadata的测试并不充分
        Assert.assertFalse(amd.isAbstract());
        Assert.assertFalse(amd.isFinal());
        Assert.assertEquals(Constant.v4PetStoreServiceClassName, amd.getClassName());
    }

}
