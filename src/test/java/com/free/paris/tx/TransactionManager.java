package com.free.paris.tx;

import com.free.paris.util.MessageTracker;

public class TransactionManager {

    public void start() {
        System.out.println("start tx");
        MessageTracker.addMsg("start tx");
    }

    public void commit() {
        System.out.println("commit tx");
        MessageTracker.addMsg("commit tx");
    }

    public void rollback() {
        System.out.println("rollback tx");
        MessageTracker.addMsg("rollback tx");
    }
}

