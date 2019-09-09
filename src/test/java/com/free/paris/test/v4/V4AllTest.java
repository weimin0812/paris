package com.free.paris.test.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ClassReaderTest.class,
                MetadataReaderTest.class,
                PackageResourceLoaderTest.class,
                ClassPathBeanDefinitionScannerTest.class,
                ApplicationContextTest.class,
                DependencyDescriptorTest.class,
                InjectionMetadataTest.class,
                AutowiredAnnotationProcessorTest.class,
        }
)

public class V4AllTest {

}
