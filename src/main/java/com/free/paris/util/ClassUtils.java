package com.free.paris.util;

public class ClassUtils {

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();

        } catch (Throwable ex) {

        }

        if (cl == null) {
            cl = ClassUtils.class.getClassLoader();

            if (cl == null) {
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Exception e) {

                }
            }
        }

        return cl;
    }
}
