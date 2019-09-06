package com.free.paris.beans.factory.config;

import com.free.paris.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {
    Object resolveDependency(DependencyDescriptor descriptor);
}
