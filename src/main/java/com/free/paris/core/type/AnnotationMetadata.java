package com.free.paris.core.type;

import com.free.paris.core.annotation.AnnotationAttributes;

import java.util.Set;

public interface AnnotationMetadata extends ClassMetadata {
    Set<String> getAnnotationTypes();

    boolean hasAnnotation(String annotationType);

    AnnotationAttributes getAnnotationAttributes(String annotationType);
}
