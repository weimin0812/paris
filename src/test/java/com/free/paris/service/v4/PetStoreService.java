package com.free.paris.service.v4;

import com.free.paris.beans.factory.annotation.Autowired;
import com.free.paris.dao.v4.AccountDAO;
import com.free.paris.dao.v4.ItemDAO;
import com.free.paris.stereotype.Component;

@Component(value = "petStore")
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
}
