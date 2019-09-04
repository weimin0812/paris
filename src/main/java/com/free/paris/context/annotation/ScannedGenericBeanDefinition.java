package com.free.paris.context.annotation;

import com.free.paris.beans.factory.support.GenericBeanDefinition;
import com.free.paris.core.type.AnnotationMetadata;

public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {
    private final AnnotationMetadata metadata;

    public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
        super();
        this.metadata = metadata;
        setBeanClassName(this.metadata.getClassName());
    }

    @Override
    public AnnotationMetadata getMetadata() {
        return metadata;
    }


}
