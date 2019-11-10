package com.free.paris.test.v5;

import com.free.paris.test.v4.ApplicationContextTest;
import com.free.paris.test.v4.AutowiredAnnotationProcessorTest;
import com.free.paris.test.v4.ClassPathBeanDefinitionScannerTest;
import com.free.paris.test.v4.ClassReaderTest;
import com.free.paris.test.v4.DependencyDescriptorTest;
import com.free.paris.test.v4.InjectionMetadataTest;
import com.free.paris.test.v4.MetadataReaderTest;
import com.free.paris.test.v4.PackageResourceLoaderTest;
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

public class V5AllTest {

}
