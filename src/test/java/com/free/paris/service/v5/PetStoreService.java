package com.free.paris.service.v5;


import com.free.paris.beans.factory.annotation.Autowired;
import com.free.paris.dao.v5.AccountDAO;
import com.free.paris.dao.v5.ItemDAO;
import com.free.paris.stereotype.Component;
import com.free.paris.util.MessageTracker;

@Component
public class PetStoreService {
    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ItemDAO itemDAO;


    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public ItemDAO getItemDAO() {
        return itemDAO;
    }

    public void placeOrder() {
        System.out.println("place order");
        MessageTracker.addMsg("place order");
    }

    public void placeOrderWithException() {
        throw new NullPointerException();
    }
}
