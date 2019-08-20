package com.free.paris.test.v1;

import com.free.paris.core.io.ClassPathResource;
import com.free.paris.core.io.FileSystemResource;
import com.free.paris.core.io.Resource;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

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
}
