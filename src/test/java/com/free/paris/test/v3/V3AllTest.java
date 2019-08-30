package com.free.paris.test.v3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConstructorResolverTest.class,
        BeanDefinitionTestV3.class,
        ApplicationContextTestV3.class
}
)
public class V3AllTest {

}
