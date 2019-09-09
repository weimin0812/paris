package com.free.paris.beans.factory.annotation;

import com.free.paris.beans.BeansException;
import com.free.paris.beans.factory.BeanCreationException;
import com.free.paris.beans.factory.config.AutowireCapableBeanFactory;
import com.free.paris.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.free.paris.util.AnnotationUtils;
import com.free.paris.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {
    private AutowireCapableBeanFactory beanFactory;

    private String requiredParameterName = "required";

    private boolean requiredParameterValue = true;

    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>();

    public AutowiredAnnotationProcessor() {
        autowiredAnnotationTypes.add(Autowired.class);
    }


    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean afterInstantiation(Object bean, String beanName) throws BeansException {
        return false;
    }

    @Override
    public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {
        InjectionMetadata injectionMetadata = buildAutowiringMetadata(bean.getClass());
        try {
            injectionMetadata.inject(bean);
        } catch (Exception e) {
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", e);
        }
    }

    @Override
    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object afterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    public InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {
        LinkedList<InjectionElement> elements = new LinkedList<>();
        Class<?> targetClass = clazz;

        do {
            LinkedList<InjectionElement> curElements = new LinkedList<>();
            for (Field field : targetClass.getDeclaredFields()) {
                Annotation anno = findAutowiredAnnotation(field);
                if (anno != null) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }

                    boolean required = determinedRequiredStatus(anno);
                    curElements.add(new AutowiredFieldElement(field, required, beanFactory));
                }
            }
            elements.addAll(0, curElements);
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);

        return new InjectionMetadata(clazz, elements);
    }

    private boolean determinedRequiredStatus(Annotation ann) {
        try {
            Method method = ReflectionUtils.findMethod(ann.annotationType(), this.requiredParameterName);
            if (method == null) {
                // Annotations like @Inject and @Value don't have a method (attribute) named "required"
                // -> default to required status
                return true;
            }
            return (this.requiredParameterValue == (Boolean) ReflectionUtils.invokeMethod(method, ann));
        } catch (Exception ex) {
            // An exception was thrown during reflective invocation of the required attribute
            // -> default to required status
            return true;
        }
    }

    private Annotation findAutowiredAnnotation(AccessibleObject ao) {
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
            Annotation ann = AnnotationUtils.getAnnotation(ao, type);
            if (ann != null) {
                return ann;
            }
        }
        return null;
    }

}
