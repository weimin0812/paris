package com.free.paris.core.type;

public interface ClassMetadata {
    String getClassName();

    boolean isInterface();

    boolean isAbstract();

    boolean isFinal();

    boolean hasSuperClass();

    String getSuperClassName();

    String[] getInterfaceNames();

}
