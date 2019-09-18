package com.free.paris.test.v5;

import com.free.paris.aop.MethodMatcher;
import com.free.paris.aop.aspectj.AspectJExpressionPointcut;
import com.free.paris.service.v5.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

public class PointcutTest {

    @Test
    public void testPointcut() throws NoSuchMethodException {
        String expression = "execution(* com.free.paris.service.v5.*.placeOrder(..))";
        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);

        MethodMatcher mm = pc.getMethodMatcher();

        Class<?> targetClass = PetStoreService.class;
        Method placeOrderMethod = targetClass.getMethod("placeOrder");
        Assert.assertTrue(mm.matches(placeOrderMethod));

        Method getAccountDAOMethod = targetClass.getMethod("getAccountDAO");
        Assert.assertFalse(mm.matches(getAccountDAOMethod));


    }


}
