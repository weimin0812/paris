package com.free.paris.core.type.classreading;

import com.free.paris.core.io.Resource;
import com.free.paris.core.type.AnnotationMetadata;
import com.free.paris.core.type.ClassMetadata;

public interface MetadataReader {
    Resource getResource();

    ClassMetadata getClassMetadata();

    AnnotationMetadata getAnnotationMetadata();
}
