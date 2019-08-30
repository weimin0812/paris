package com.free.paris.beans.factory.support;

import com.free.paris.beans.ConstructorArgument;
import com.free.paris.beans.SimpleTypeConverter;
import com.free.paris.beans.factory.BeanCreationException;
import com.free.paris.beans.BeanDefinition;
import com.free.paris.beans.factory.config.ConfigurableBeanFactory;

import java.lang.reflect.Constructor;
import java.util.List;

public class ConstructorResolver {
    private final ConfigurableBeanFactory beanFactory;

    public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object autowireConstructor(final BeanDefinition bd) {
        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;

        Class<?> beanClass = null;

        try {
            beanClass = beanFactory.getBeanClassLoader().loadClass(bd.getBeanClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new BeanCreationException(bd.getId(), "Instantiation of bean failed, can't resolve class", e);
        }

        Constructor<?>[] candidates = beanClass.getConstructors();

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(beanFactory);
        ConstructorArgument cargs = bd.getConstructorArgument();
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        for (Constructor<?> candidate : candidates) {
            Class<?>[] parameterTypes = candidate.getParameterTypes();
            if (parameterTypes.length != cargs.getArgumentCount()) {
                continue;
            }

            argsToUse = new Object[parameterTypes.length];
            boolean result = valueMatchType(parameterTypes,
                    cargs.getArgumentValues(),
                    argsToUse,
                    valueResolver,
                    typeConverter
            );

            if (result) {
                constructorToUse = candidate;
                break;
            }
        }

        if (constructorToUse == null) {
            throw new BeanCreationException(bd.getId(), "can't find a apporiate constructor");
        }

        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException(bd.getId(), "can't find a create instance using " + constructorToUse);
        }
    }

    private boolean valueMatchType(Class<?>[] parameterTypes,
                                   List<ConstructorArgument.ValueHolder> argumentValues,
                                   Object[] argsToUse,
                                   BeanDefinitionValueResolver valueResolver,
                                   SimpleTypeConverter typeConverter) {
        for (int i = 0; i < parameterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder = argumentValues.get(i);
            try {
                Object originalValue = valueHolder.getValue();
                Object resolveValue = valueResolver.resolveValueIfNecessary(originalValue);
                Object convertValue = typeConverter.convertIfNecessary(resolveValue, parameterTypes[i]);
                argsToUse[i] = convertValue;
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }
}
