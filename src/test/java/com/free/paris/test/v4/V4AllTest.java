package com.free.paris.test.v4;

import com.free.paris.core.type.classreading.MetadataReader;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ClassReaderTest.class,
                MetadataReader.class,
                PackageResourceLoaderTest.class,
                ClassPathBeanDefinitionScannerTest.class,
                ApplicationContextTest.class
        }
)

public class V4AllTest {

}
