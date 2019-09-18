package com.free.paris.test.v5;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                PointCutTest.class,
                MethodLocatingFactoryTest.class,
                CGLibTest.class,
                ReflectiveMethodInvocationTest.class,
                CGLibTest.class
        }
)
public class V5AllTest {

}
