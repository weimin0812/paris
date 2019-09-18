package com.free.paris.test.v5;

import com.free.paris.aop.config.MethodLocatingFactory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PointcutTest.class,
        MethodLocatingFactory.class,
        ApplicationContextText5.class
})
public class V5AllTest {

}
