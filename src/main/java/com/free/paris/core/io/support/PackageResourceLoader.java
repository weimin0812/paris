package com.free.paris.core.io.support;

import com.free.paris.core.io.FileSystemResource;
import com.free.paris.core.io.Resource;
import com.free.paris.util.Assert;
import com.free.paris.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PackageResourceLoader {
    private final ClassLoader classLoader;

    public PackageResourceLoader(ClassLoader classLoader) {
        Assert.notNull(classLoader, "ResourceLoader must not be null");
        this.classLoader = classLoader;
    }

    public PackageResourceLoader() {
        classLoader = ClassUtils.getDefaultClassLoader();
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public Resource[] getResources(String basePackage) throws IOException {
        Assert.notNull(basePackage, "basePackage  must not be null");
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        ClassLoader cl = getClassLoader();
        URL url = cl.getResource(location);
        File rootDir = new File(url.getFile());
        Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
        Resource[] result = new Resource[matchingFiles.size()];
        matchingFiles.stream().map(f -> new FileSystemResource(f)).collect(Collectors.toList()).toArray(result);
        return result;
    }

    private Set<File> retrieveMatchingFiles(File rootDir) {
        if (!rootDir.exists() || !rootDir.isDirectory() || !rootDir.canRead()) {
            return Collections.emptySet();
        }

        Set<File> result = new LinkedHashSet<>(8);
        doRetrieveMatchingFiles(rootDir, result);
        return result;
    }

    private void doRetrieveMatchingFiles(File dir, Set<File> result) {
        File[] dirContents = dir.listFiles();
        if (dirContents == null) {
            return;
        }

        for (File content : dirContents) {
            if (content.isDirectory()) {
                if (content.canRead()) {
                    doRetrieveMatchingFiles(content, result);
                }
            } else {
                result.add(content);
            }
        }

    }
}
