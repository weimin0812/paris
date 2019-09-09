package com.free.paris.test.v4;

import com.free.paris.beans.factory.annotation.AutowiredAnnotationProcessor;
import com.free.paris.beans.factory.annotation.AutowiredFieldElement;
import com.free.paris.beans.factory.annotation.InjectionElement;
import com.free.paris.beans.factory.annotation.InjectionMetadata;
import com.free.paris.beans.factory.config.DependencyDescriptor;
import com.free.paris.beans.factory.support.DefaultBeanFactory;
import com.free.paris.dao.v4.AccountDAO;
import com.free.paris.dao.v4.ItemDAO;
import com.free.paris.service.v4.PetStoreService;
import com.free.paris.util.Constant;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

public class AutowiredAnnotationProcessorTest {
    AccountDAO accountDAO = new AccountDAO();
    ItemDAO itemDAO = new ItemDAO();
    DefaultBeanFactory beanFactory = new DefaultBeanFactory() {
        @Override
        public Object resolveDependency(DependencyDescriptor descriptor) {
            if (descriptor.getDependencyType().equals(AccountDAO.class)) {
                return accountDAO;
            }

            if (descriptor.getDependencyType().equals(ItemDAO.class)) {
                return itemDAO;
            }

            throw new RuntimeException("can't support types except AccountDAo and ItemDAO");
        }
    };

    @Test
    public void testGetInjectionMetadata() {
        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(beanFactory);
        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PetStoreService.class);
        List<InjectionElement> elements = injectionMetadata.getInjectionElements();
        assertFieldExists(elements, Constant.ACCOUNT_DAO);
        assertFieldExists(elements, Constant.ITEM_DAO);

        PetStoreService petStore = new PetStoreService();

        injectionMetadata.inject(petStore);

        Assert.assertTrue(petStore.getAccountDAO() instanceof AccountDAO);

        Assert.assertTrue(petStore.getItemDAO() instanceof ItemDAO);
    }

    private void assertFieldExists(List<InjectionElement> elements, String fieldName) {
        for (InjectionElement ele : elements) {
            AutowiredFieldElement fieldEle = (AutowiredFieldElement) ele;
            Field f = fieldEle.getField();
            if (f.getName().equals(fieldName)) {
                return;
            }
        }
        Assert.fail(fieldName + "does not exist!");
    }


}
