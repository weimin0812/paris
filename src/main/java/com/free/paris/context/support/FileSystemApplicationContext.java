package com.free.paris.context.support;

import com.free.paris.core.io.FileSystemResource;
import com.free.paris.core.io.Resource;

public class FileSystemApplicationContext extends AbstractApplicationContext {
    public FileSystemApplicationContext(String path) {
        super(path);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }
}
