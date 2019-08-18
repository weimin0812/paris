package com.free.paris.core.io;

import com.free.paris.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemResource implements Resource {
    private final String path;
    private final File file;

    public FileSystemResource(String path) {
        Assert.notNull(path, "Path must not be null");
        this.path = path;
        file = new File(path);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public String getDescription() {
        return "file [" + file.getAbsolutePath() + "]";
    }
}
