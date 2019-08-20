package com.free.paris.context.support;

import com.free.paris.core.io.ClassPathResource;
import com.free.paris.core.io.Resource;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path, getBeanClassLoader());
    }
}
