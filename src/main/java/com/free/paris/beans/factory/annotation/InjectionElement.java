package com.free.paris.beans.factory.annotation;

import com.free.paris.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

public abstract class InjectionElement {
    protected Member member;
    protected AutowireCapableBeanFactory factory;

    public InjectionElement(Member member, AutowireCapableBeanFactory factory) {
        this.member = member;
        this.factory = factory;
    }

    public abstract void inject(Object target);
}
