package com.free.paris.test.v2;

import com.free.paris.beans.factory.support.BeanDefinitionValueResolver;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CustomBooleanEditorTest.class,
        CustomNumberEditorTest.class,
        TypeConverterTest.class,
        BeanDefinitionValueResolverTest.class,
        BeanDefinitionTestV2.class,
        ApplicationContextTestV2.class

})
public class V2AllTest {

}
