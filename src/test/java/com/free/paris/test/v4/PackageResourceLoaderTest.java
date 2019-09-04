package com.free.paris.test.v4;

import com.free.paris.core.io.Resource;
import com.free.paris.core.io.support.PackageResourceLoader;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PackageResourceLoaderTest {
    @Test
    public void testGetResources() throws IOException {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources(Constant.v4DaoBasePackage);
        Assert.assertEquals(2, resources.length);
    }
}
