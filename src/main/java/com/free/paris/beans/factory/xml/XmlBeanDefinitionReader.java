package com.free.paris.beans.factory.xml;

import com.free.paris.beans.BeanDefinition;
import com.free.paris.beans.ConstructorArgument;
import com.free.paris.beans.PropertyValue;
import com.free.paris.beans.factory.BeanDefinitionStoreException;
import com.free.paris.beans.factory.config.RuntimeBeanReference;
import com.free.paris.beans.factory.config.TypedStringValue;
import com.free.paris.beans.factory.support.BeanDefinitionRegistry;
import com.free.paris.beans.factory.support.GenericBeanDefinition;
import com.free.paris.context.support.ClassPathBeanDefinitionScanner;
import com.free.paris.core.io.Resource;
import com.free.paris.util.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XmlBeanDefinitionReader {
    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";

    public static final String SCOPE_ATTRIBUTE = "scope";

    public static final String PROPERTY_ELEMENT = "property";

    public static final String REF_ATTRIBUTE = "ref";

    public static final String VALUE_ATTRIBUTE = "value";

    public static final String NAME_ATTRIBUTE = "name";

    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";

    public static final String TYPE_ATTRIBUTE = "type";

    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";

    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinitions(Resource resource) {
        InputStream is = null;
        try {
            is = resource.getInputStream();
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            Element root = doc.getRootElement();
            Iterator<Element> iter = root.elementIterator();
            while (iter.hasNext()) {
                Element ele = iter.next();
                String namespaceURI = ele.getNamespaceURI();
                if (isDefaultNamespace(namespaceURI)) {
                    parseDefaultElement(ele);
                } else if (isContextNamespace(namespaceURI)) {
                    parseComponentElement(ele);
                }

            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource.getDescription(), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parseComponentElement(Element ele) throws IOException {
        String basePackages = ele.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.doScan(basePackages);
    }

    private void parseDefaultElement(Element ele) {
        String id = ele.attributeValue(ID_ATTRIBUTE);
        String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
        GenericBeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
        if (ele.attributeValue(SCOPE_ATTRIBUTE) != null) {
            bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
        }
        parseConstructorArgElements(ele, bd);
        parsePropertyElement(ele, bd);
        registry.registerBeanDefinition(id, bd);
    }

    private boolean isContextNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
    }

    private boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    private void parseConstructorArgElements(Element ele, GenericBeanDefinition bd) {
        Iterator<Element> iterator = ele.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while (iterator.hasNext()) {
            Element element = iterator.next();
            parseConstructorArgElement(element, bd);
        }
    }

    private void parseConstructorArgElement(Element element, GenericBeanDefinition bd) {
        String typeAttr = element.attributeValue(TYPE_ATTRIBUTE);
        String nameAttr = element.attributeValue(NAME_ATTRIBUTE);
        Object val = parsePropertyValue(element, bd, null);
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(val);
        if (StringUtils.hasLength(typeAttr)) {
            valueHolder.setType(typeAttr);
        }
        if (StringUtils.hasLength(nameAttr)) {
            valueHolder.setName(nameAttr);
        }

        bd.getConstructorArgument().addArgumentValue(valueHolder);
    }

    private void parsePropertyElement(Element beanElem, BeanDefinition bd) {
        Iterator<Element> iter = beanElem.elementIterator(PROPERTY_ELEMENT);
        while (iter.hasNext()) {
            Element propElem = iter.next();
            String propertyName = propElem.attributeValue(NAME_ATTRIBUTE);
            if (!StringUtils.hasLength(propertyName)) {
                return;
            }

            Object val = parsePropertyValue(propElem, bd, propertyName);
            PropertyValue pv = new PropertyValue(propertyName, val);
            bd.getPropertyValues().add(pv);
        }

    }

    private Object parsePropertyValue(Element ele, BeanDefinition bd, String propertyName) {
        String elementName = (propertyName != null) ?
                "<property> element for property '" + propertyName + "'" :
                "<constructor-arg> element";
        boolean ref = ele.attributeValue(REF_ATTRIBUTE) != null;
        boolean val = ele.attributeValue(VALUE_ATTRIBUTE) != null;
        if (ref) {
            String refName = ele.attributeValue(REF_ATTRIBUTE);
            return new RuntimeBeanReference(refName);
        }

        if (val) {
            return new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
        }

        throw new RuntimeException(elementName + " must specify a ref or value");
    }
}
