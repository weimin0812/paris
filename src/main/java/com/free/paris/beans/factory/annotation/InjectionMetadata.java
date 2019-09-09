package com.free.paris.beans.factory.annotation;

import java.util.List;

public class InjectionMetadata {
    private final Class<?> targetClass;
    private List<InjectionElement> injectionElements;

    public InjectionMetadata(Class<?> targetClass, List<InjectionElement> elements) {
        this.targetClass = targetClass;
        this.injectionElements = elements;
    }

    public List<InjectionElement> getInjectionElements() {
        return injectionElements;
    }

    public void inject(Object target) {
        if (injectionElements == null || injectionElements.isEmpty()) {
            return;
        }
        injectionElements.stream().forEach(i -> i.inject(target));
    }
}
