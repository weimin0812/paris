package com.free.paris.beans;

import com.free.paris.beans.ConstructorArgument;
import com.free.paris.beans.PropertyValue;

import java.util.List;

public interface BeanDefinition {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    String SCOPE_DEFAULT = "";

    boolean isSingleton();

    boolean isPrototype();

    String getScope();

    void setScope(String scope);

    String getBeanClassName();

    List<PropertyValue> getPropertyValues();

    String getId();

    ConstructorArgument getConstructorArgument();

    boolean hasConstructorArgumentValues();
}
