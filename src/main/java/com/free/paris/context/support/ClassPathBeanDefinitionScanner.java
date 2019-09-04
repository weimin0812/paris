package com.free.paris.context.support;

import com.free.paris.beans.BeanDefinition;
import com.free.paris.beans.factory.support.BeanDefinitionRegistry;
import com.free.paris.beans.factory.support.BeanNameGenerator;
import com.free.paris.context.annotation.AnnotationBeanNameGenerator;
import com.free.paris.context.annotation.ScannedGenericBeanDefinition;
import com.free.paris.core.io.Resource;
import com.free.paris.core.io.support.PackageResourceLoader;
import com.free.paris.core.type.classreading.MetadataReader;
import com.free.paris.core.type.classreading.SimpleMetadataReader;
import com.free.paris.stereotype.Component;
import com.free.paris.util.StringUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathBeanDefinitionScanner {
    private final BeanDefinitionRegistry registry;

    private PackageResourceLoader resourceLoader = new PackageResourceLoader();

    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefinition> doScan(String packagesToScan) throws IOException {
        String[] basePackages = StringUtils.tokenizeToStringArray(packagesToScan, ",");
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                beanDefinitions.add(candidate);
                registry.registerBeanDefinition(candidate.getId(), candidate);
            }
        }

        return beanDefinitions;
    }

    private Set<BeanDefinition> findCandidateComponents(String basePackage) throws IOException {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Resource[] resources = resourceLoader.getResources(basePackage);
        for (Resource resource : resources) {
            MetadataReader metadataReader = new SimpleMetadataReader(resource);
            if (metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {
                ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader.getAnnotationMetadata());
                String beanName = beanNameGenerator.generateBeanName(sbd, registry);
                sbd.setId(beanName);
                candidates.add(sbd);
            }
        }

        return candidates;
    }
}
