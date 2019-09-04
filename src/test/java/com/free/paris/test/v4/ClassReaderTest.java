package com.free.paris.test.v4;

import com.free.paris.core.io.ClassPathResource;
import com.free.paris.core.type.classreading.ClassMetadataReadingVisitor;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.asm.ClassReader;

import java.io.IOException;

public class ClassReaderTest {
    @Test
    public void testGetClassMetaData() throws IOException {
        ClassPathResource resource = new ClassPathResource(Constant.v4PetStoreServiceClassPath);
        ClassReader reader = new ClassReader(resource.getInputStream());
        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();
        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertEquals(Constant.v4PetStoreServiceClassName, visitor.getClassName());
        Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
        Assert.assertEquals(0, visitor.getInterfaceNames().length);
    }
}
