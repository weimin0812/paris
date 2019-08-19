package com.free.paris.test.v1;

import com.free.paris.core.io.ClassPathResource;
import com.free.paris.core.io.FileSystemResource;
import com.free.paris.core.io.Resource;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

public class ResourceTest {
    @Test
    public void testClassPathResource() throws Exception {
        Resource r = new ClassPathResource(Constant.petstoreV1);

        InputStream is = null;
        try {
            is = r.getInputStream();
            Assert.assertNotNull(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    @Test
    public void testFileSystemResource() throws Exception {
        String filePath = Constant.localPetstoreV1;
        Resource r = new FileSystemResource(filePath);
        InputStream is = null;
        try {
            is = r.getInputStream();
            Assert.assertNotNull(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    @Test
    public void printResourcePath() {
        Properties ps = System.getProperties();
        System.out.println(ps.getProperty("sun.boot.class.path"));
        System.out.println(ps.getProperty("java.ext.dirs"));
        System.out.println(ps.getProperty("java.class.path"));
    }

    @Test
    public void testClassLoader() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class p = classLoader.loadClass("com.free.paris.service.v1.PetStoreService");
            System.out.println(0);
        } catch (Exception e) {

        }

    }
}
