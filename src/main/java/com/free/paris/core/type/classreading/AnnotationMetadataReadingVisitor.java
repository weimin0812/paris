package com.free.paris.core.type.classreading;

import com.free.paris.core.annotation.AnnotationAttributes;
import com.free.paris.core.type.AnnotationMetadata;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.Type;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements AnnotationMetadata {
    private final Set<String> annotationSet = new LinkedHashSet<>(4);
    private final Map<String, AnnotationAttributes> attributesMap = new LinkedHashMap<>(4);

    public AnnotationMetadataReadingVisitor() {
    }

    @Override
    public AnnotationVisitor visitAnnotation(final String desc, boolean visible) {
        String className = Type.getType(desc).getClassName();
        annotationSet.add(className);
        return new AnnotationAttributesReadingVisitor(className, attributesMap);
    }

    @Override
    public Set<String> getAnnotationTypes() {
        return annotationSet;
    }

    @Override
    public boolean hasAnnotation(String annotationType) {
        return annotationSet.contains(annotationType);
    }

    @Override
    public AnnotationAttributes getAnnotationAttributes(String annotationType) {
        return attributesMap.get(annotationType);
    }
}
